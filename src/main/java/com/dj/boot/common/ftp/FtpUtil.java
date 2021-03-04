package com.dj.boot.common.ftp;

import com.dj.boot.common.util.math.DoubleComparer;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FtpUtil {
    public FTPClient ftpClient = new FTPClient();
    /** 文本传送过程中在服务器端的临时前缀 */
    private String temp_OP;
    /** 文本传送过程中在服务器端的临时后缀 */
    private String temp_ED;
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    private String charset="GBK";

    public FtpUtil() {
        this.temp_ED = "";
        this.temp_OP = "";
        // 设置将过程中使用到的命令输出到控制台
        try {
            this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(new OutputStreamWriter(System.out,charset))));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 带前缀和后缀的构造方法
     * @param op 前缀
     * @param ed 后缀
     */
    public FtpUtil(String op, String ed) {
        this.temp_OP = op;
        this.temp_ED = ed;
        try {
            this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(new OutputStreamWriter(System.out,charset))));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 连接到FTP服务器
     *
     * @param hostname 		主机名
     * @param port     		端口
     * @param username 		用户名
     * @param password 		密码
     * @return boolean 		是否连接成功
     * @throws IOException
     */
    public boolean connect(String hostname, int port, String username, String password) throws IOException {
        ftpClient.connect(hostname, port);
        ftpClient.setControlEncoding("UTF-8");
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            if (ftpClient.login(username, password)) {
                return true;
            }
        }
        disconnect();
        return false;
    }

    /**
     * 断开与远程服务器的连接
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报(整个文件夹下文件下载)
     * @param remote 远程文件夹路径
     * @param local 本地文件夹路径
     * @return 下载成功文件列表
     * @throws IOException
     */
    public String[] download(String remote, String local) throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        List<String> results = new ArrayList<>();

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), StandardCharsets.ISO_8859_1));

        for (int i = 0; i < files.length; i++) {
            String remoteFile = files[i].getName();
            String localFile = files[i].getName();
            if ((!"".equals(temp_OP)&&remoteFile.startsWith(temp_OP))
                    || (!"".equals(temp_ED)&&remoteFile.endsWith(temp_ED))) {
                // 临时文件不下载
                continue;
            }
            DownloadStatus downloadStatus = downloadFile(remote+remoteFile, local+localFile);
            if (downloadStatus == DownloadStatus.Download_From_Break_Success
                    || downloadStatus == DownloadStatus.Download_New_Success) {
                results.add(remoteFile);
            } else {
                logger.error("下载文件"+remoteFile+"状态:"+downloadStatus.toString());
            }
        }
        return results.toArray(new String[results.size()]);
    }
    /**
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报(单个文件)
     *
     * @param remote   			远程文件路径
     * @param local    			本地文件路径
     * @return DownloadStatus   上传的状态
     * @throws IOException
     */
    public DownloadStatus downloadFile(String remote, String local)
            throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        DownloadStatus result;

        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(new String(
                remote.getBytes("UTF-8"), "iso-8859-1"));
        if (files.length != 1) {
            logger.info("远程文件不存在");
            return DownloadStatus.Remote_File_Noexist;
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(local);
        // 本地存在文件，进行断点下载
        if (f.exists()) {
            long localSize = f.length();
            // 判断本地文件大小是否大于远程文件大小
            if (localSize >= lRemoteSize) {
                logger.info("本地文件大于远程文件，下载中止");
                return DownloadStatus.Local_Bigger_Remote;
            }

            // 进行断点续传，并记录状态
            FileOutputStream out = null;
            InputStream in = null;
            try {
                out = new FileOutputStream(f, true);
                ftpClient.setRestartOffset(localSize);
                in = ftpClient.retrieveFileStream(new String(remote
                        .getBytes("UTF-8"), "iso-8859-1"));
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                long process = localSize / step;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 50 == 0)
                            logger.info("下载进度：" + process);
                        // 更新文件下载进度,值存放在process变量中
                    }
                }
            } catch(Exception e) {
                logger.error("文件下载失败", e);
            } finally {
                if (in != null) in.close();
                if (out != null) out.close();
            }
            boolean isDo = ftpClient.completePendingCommand();
            if (isDo) {
                logger.info("断点下载文件成功");
                result = DownloadStatus.Download_From_Break_Success;
            } else {
                logger.info("断点下载文件失败");
                result = DownloadStatus.Download_From_Break_Failed;
            }
        } else {
            OutputStream out = null;
            InputStream in = null;
            try {
                out = new FileOutputStream(f);
                in = ftpClient.retrieveFileStream(new String(remote
                        .getBytes("UTF-8"), "iso-8859-1"));
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                long process = 0;
                long localSize = 0L;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 50 == 0)
                            logger.info("下载进度：" + process + "%");
                        // 更新文件下载进度,值存放在process变量中
                    }
                }
            } catch(Exception e) {
                logger.error("文件下载失败", e);
            } finally {
                if (in != null) in.close();
                if (out != null) out.close();
            }
            boolean upNewStatus = ftpClient.completePendingCommand();
            if (upNewStatus) {
                logger.info("全新下载文件成功");
                result = DownloadStatus.Download_New_Success;
            } else {
                logger.info("全新下载文件失败");
                result = DownloadStatus.Download_New_Failed;
            }
        }
        return result;
    }

    /**
     * 上传文件到FTP服务器，支持断点续传
     *
     * @param local   		 本地文件名称，绝对路径
     * @param remote  		 远程文件路径，使用/home/directory1/subdirectory/file.ext，
     * 						 按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
     * @return UploadStatus  上传结果
     * @throws Exception
     */
    public UploadStatus upload(String local, String remote) throws Exception {
        // 设置PassiveMode传输
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制流的方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding("UTF-8");
        UploadStatus result;
        // 对远程目录的处理
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf('/') + 1);
            // 创建服务器远程目录结构，创建失败直接返回
            if (createDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
                logger.info("远程服务器相应目录创建失败");
                return UploadStatus.Create_Directory_Fail;
            }
        }

        // 检查远程是否存在文件
        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
                .getBytes("UTF-8"), "iso-8859-1"));
        if (files.length == 1) {
            long remoteSize = files[0].getSize();
            File f = new File(local);
            long localSize = f.length();
            if (remoteSize == localSize) {
                logger.info("文件已经存在");
                return UploadStatus.File_Exits;
            } else if (remoteSize > localSize) {
                logger.info("远程文件大于本地文件");
                return UploadStatus.Remote_Bigger_Local;
            }

            // 尝试移动文件内读取指针,实现断点续传
            result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

            // 如果断点续传没有成功，则删除服务器上文件，重新上传
            if (result == UploadStatus.Upload_From_Break_Failed) {
                if (!ftpClient.deleteFile(remoteFileName)) {
                    logger.info("删除远程文件失败");
                    return UploadStatus.Delete_Remote_Faild;
                }
                result = uploadFile(remoteFileName, f, ftpClient, 0);
            }
        } else {
            result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
        }
        //重命名文件
        String newFileName = remoteFileName.replace(this.temp_OP, "").replace(this.temp_ED, "");
        if(!remoteFileName.equals(newFileName) &&
                (result.equals(UploadStatus.Upload_New_File_Success) ||
                        result.equals(UploadStatus.Upload_From_Break_Success))){
            this.alterFileName(remoteFileName, newFileName);
        }
        return result;
    }

    /**
     * 递归创建远程服务器目录
     *
     * @param remote       		远程服务器文件绝对路径
     * @param ftpClient    		FTPClient对象
     * @return UploadStatus		目录创建是否成功
     * @throws IOException
     */
    public UploadStatus createDirecroty(String remote, FTPClient ftpClient) throws IOException {
        UploadStatus status = UploadStatus.Create_Directory_Success;
        String directory = remote.substring(0, remote.lastIndexOf('/') + 1);
        if (!directory.equalsIgnoreCase("/")
                && !ftpClient.changeWorkingDirectory(new String(directory
                .getBytes("UTF-8"), "iso-8859-1"))) {
            // 如果远程目录不存在，则递归创建远程服务器目录
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf('/', start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end)
                        .getBytes("UTF-8"), "iso-8859-1");
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        logger.info("创建目录失败");
                        return UploadStatus.Create_Directory_Fail;
                    }
                }

                start = end + 1;
                end = directory.indexOf('/', start);

                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFile   远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile    本地文件File句柄，绝对路径
     * @param ftpClient    FTPClient引用
     * @return UploadStatus
     * @throws IOException
     */
    public UploadStatus uploadFile(String remoteFile, File localFile,
                                   FTPClient ftpClient, long remoteSize) throws IOException {
        UploadStatus status;
        // 显示进度的上传
        double step = localFile.length() / 100.0;
        double process = 0;
        double localreadbytes = 0L;
        RandomAccessFile raf = new RandomAccessFile(localFile, "r");
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("UTF-8"), "iso-8859-1"));
        // 断点续传
        if (remoteSize > 0) {
            ftpClient.setRestartOffset(remoteSize);
            process = remoteSize / step;
            raf.seek(remoteSize);
            localreadbytes = remoteSize;
        }
        byte[] bytes = new byte[1024];
        int c;
        while ((c = raf.read(bytes)) != -1) {
            out.write(bytes, 0, c);
            localreadbytes += c;
            if (!DoubleComparer.considerEqual(localreadbytes / step,process)) {
                process = localreadbytes / step;
                if (process%50 == 0){
                    logger.info("上传进度:" + (int)process + "%");  // 汇报上传状态
                }
            }
        }
        out.flush();
        raf.close();
        out.close();
        boolean result = ftpClient.completePendingCommand();
        if (remoteSize > 0) {
            status = result ? UploadStatus.Upload_From_Break_Success :
                    UploadStatus.Upload_From_Break_Failed;
            if (result) {
                logger.info("断点续传成功");
            } else {
                logger.info("断点续传失败");
            }
        } else {
            status = result ? UploadStatus.Upload_New_File_Success : UploadStatus.Upload_New_File_Failed;
            if (result) {
                logger.info("上传新文件成功");
            } else {
                logger.info("上传新文件失败");
            }
        }
        return status;
    }

    // 枚举类UploadStatus代码
    public enum UploadStatus {
        Fail,
        Create_Directory_Fail, 		// 远程服务器相应目录创建失败
        Create_Directory_Success, 	// 远程服务器创建目录成功
        Upload_New_File_Success, 	// 上传新文件成功
        Upload_New_File_Failed, 	// 上传新文件失败
        File_Exits, 				// 文件已经存在
        Remote_Bigger_Local, 		// 远程文件大于本地文件
        Upload_From_Break_Success, 	// 断点续传成功
        Upload_From_Break_Failed, 	// 断点续传失败
        Delete_Remote_Faild, 		// 删除远程文件失败
        Empty_File_Upload;          // 上传空文件
    }

    // 枚举类DownloadStatus代码
    public enum DownloadStatus {
        Remote_File_Noexist, 		// 远程文件不存在
        Local_Bigger_Remote, 		// 本地文件大于远程文件
        Download_From_Break_Success,// 断点下载文件成功
        Download_From_Break_Failed, // 断点下载文件失败
        Download_New_Success, 		// 全新下载文件成功
        Download_New_Failed; 		// 全新下载文件失败
    }

    public boolean deleteFile(String pathName) throws IOException {
        return ftpClient.deleteFile(pathName);
    }

    public String alterFileName(String oldName, String newName) throws Exception {
        FTPFile[] files = ftpClient.listFiles(new String(newName.getBytes("UTF-8"), "iso-8859-1"));
        if (files.length == 1) {
            deleteFile(oldName);
            UploadStatus.File_Exits.toString();
        }else if( ftpClient.rename(new String(oldName.getBytes("UTF-8"),
                "iso-8859-1"), new String(newName.getBytes("UTF-8"),
                "iso-8859-1"))){
            return UploadStatus.Upload_New_File_Success.toString();
        }
        return UploadStatus.Fail.toString();
    }

    public boolean changeDirectory(String path) throws IOException {
        return ftpClient.changeWorkingDirectory(path);
    }

    public boolean createDirectory(String pathName) throws IOException {
        return ftpClient.makeDirectory(pathName);
    }

    public boolean removeDirectory(String path) throws IOException {
        return ftpClient.removeDirectory(path);
    }

    public boolean removeDirectory(String path, boolean isAll) throws IOException {
        if (!isAll) {
            return removeDirectory(path);
        }
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }
        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                logger.info("！删除路径：["+path + "/" + name+"]");
                removeDirectory(path + "/" + name, true);
            } else {
                logger.info("！删除文件：["+path + "/" + name+"]");
                deleteFile(path + "/" + name);
            }
        }
        return ftpClient.removeDirectory(path);
    }

    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String getTemp_OP() {
        return temp_OP;
    }

    public void setTemp_OP(String temp_OP) {
        this.temp_OP = temp_OP;
    }

    public String getTemp_ED() {
        return temp_ED;
    }

    public void setTemp_ED(String temp_ED) {
        this.temp_ED = temp_ED;
    }

    //测试main函数
    //--------------------------------------------------------------------------------------------------


    public void download1(HttpServletResponse response, File file) throws IOException {
        // 设置被动模式
        ftpClient.enterLocalPassiveMode();
        // 设置以二进制方式传输
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            String fileName = file.getName();
            //获取到文字 数据库里对应的附件名字加上老的文件名字：filename 截取到后面的文件类型 例：txt  组成一个新的文件名字：newFileName
//            String newFileName = fileName.substring(fileName.lastIndexOf(".")+1);
            if(!file.exists()) {
                //如果文件不存在就跳出
                return;
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + "");
            out = response.getOutputStream();
            //读取文件流
            int len;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (ips != null) {
                    ips.close();
                }
            } catch (IOException e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        FtpUtil myFtp = new FtpUtil("temp_","");
        try {
            myFtp.connect("47.105.88.104", 10088, "admin", "djxiaoxin");
//			myFtp.ftpClient.makeDirectory(new String("test20090806".getBytes("UTF-8"),"iso-8859-1"));
//			myFtp.ftpClient.removeDirectory(new String("test20090806".getBytes("UTF-8"),"iso-8859-1"));
//			myFtp.ftpClient.changeWorkingDirectory(new String("test20090806".getBytes("GBK"),"iso-8859-1"));
//			logger.info(String.valueOf(myFtp.upload("D:\\GITS-201-20150731-SpotDeals.deal","/ftp/GITS-201-20150828-SpotDeals.deal")));
//			System.out.println(myFtp.download("SystemOut.log","E:\\SystemOut.log"));
            myFtp.download("/home/admin/", "C:/zip/");
//            myFtp.downloadFile("/home/admin/ceshi.doc", "D:\\Lzg\\");
//            myFtp.download("/home/admin/ceshi.doc", "/home/admin/ceshi.doc");
            myFtp.disconnect();

            String ftpHost = "192.168.26.132";
            String ftpUserName = "zyf";
            String ftpPassword = "zyf";
            int ftpPort = 21;
            String ftpPath = "ftpdir/";
            String localPath = "H:/download";
            String fileName = "11.txt";
//            FtpUtil.downloadFtpFile("47.105.88.104", "admin", "djxiaoxin", 10088, "/home/admin/", "D:\\Lzg\\", "ceshi.doc");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接FTP出错：" + e.getMessage());
        }
    }


    //========================================单个文件下载==========================================

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost
     *            FTP主机服务器
     * @param ftpPassword
     *            FTP 登录密码
     * @param ftpUserName
     *            FTP登录用户名
     * @param ftpPort
     *            FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 从FTP服务器下载文件
     *
     * @param ftpHost FTP IP地址
     *
     * @param ftpUserName FTP 用户名
     *
     * @param ftpPassword FTP用户名密码
     *
     * @param ftpPort FTP端口
     *
     * @param ftpPath FTP服务器中文件所在路径 格式： ftptest/aa
     *
     * @param localPath 下载到本地的位置 格式：H:/download
     *
     * @param fileName 文件名称
     */
    public static void downloadFtpFile(String ftpHost, String ftpUserName,
                                       String ftpPassword, int ftpPort, String ftpPath, String localPath,
                                       String fileName, HttpServletResponse response) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.setControlEncoding("UTF-8"); // 中文支持
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ftpPath);

//            //查看当前目录
//            String workingDirectory = ftpClient.printWorkingDirectory();
//            System.out.println(workingDirectory);

//            File localFile = new File(localPath + File.separatorChar + fileName);
//            OutputStream out = new FileOutputStream(localFile);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + "");
            OutputStream out = response.getOutputStream();
            final boolean b = ftpClient.retrieveFile(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), out);
//            final boolean b = ftpClient.retrieveFile(fileName, out);
            System.out.println(b);
            out.flush();
            out.close();
            ftpClient.logout();
            logger.info("下载完成");
        } catch (FileNotFoundException e) {
            logger.error("没有找到" + ftpPath + "文件");
            e.printStackTrace();
        } catch (SocketException e) {
            logger.error("连接FTP失败.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件读取错误。");
            e.printStackTrace();
        }

    }



}