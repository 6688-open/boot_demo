package com.dj.boot.common.util.nio.pipe;

import com.dj.boot.common.util.lang.ExceptionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 
 * A thread for reverse pipe, read data from InputSteam, write data into WritableByteChannel object.
 * 
 * @author: Aldwin Su
 */
public class ReversePipeThread extends Thread {
	/**
	 * Buffer size.
	 */
	private int bufferSize = 1024;

	/**
	 * Callback when new data comes.
	 */
	private ReversePipeCallback callback;

	private boolean isShutdown = false;
	private ReadableByteChannel inputChannel;
	private WritableByteChannel outputChannel;

	public ReversePipeThread(InputStream inputStream, WritableByteChannel outputChannel) {
		this.inputChannel = Channels.newChannel(inputStream);
		this.outputChannel = outputChannel;
		this.setDaemon(true);
	}

	@Override
	public void run() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

		while (!isShutdown) {
			try {
				while (inputChannel.read(byteBuffer) > 0) {
					byteBuffer.flip();
					callback(byteBuffer);
					byteBuffer.clear();
				}
			} catch (IOException e) {
				System.out.println(ExceptionUtils.toString(e));
			}
		}
	}

	private void callback(ByteBuffer byteBuffer) throws IOException {
		if (callback != null) {
			callback.call(byteBuffer);
		}

		if (outputChannel == null) {
			return;
		}

		if (!outputChannel.isOpen()) {
			throw new IOException("The output channel has been closed!");
		}

		outputChannel.write(byteBuffer);
	}

	public void shutdown() {
		isShutdown = true;
		this.interrupt();
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public ReversePipeCallback getCallback() {
		return callback;
	}

	public void setCallback(ReversePipeCallback callback) {
		this.callback = callback;
	}
}