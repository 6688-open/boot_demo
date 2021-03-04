package com.dj.boot.test.sort;

public class Half {




    public static void main(String[] args) {
        int[] arr = { 49, 76, 1, 5, 8, 2, 0, -1, 22 };
        Sort.QuickSort(arr, 0, arr.length - 1);
//        quickSort(arr, 0, arr.length - 1);
//        System.out.println("排序后:");
//        for (int i : arr) {
//            System.out.println(i);
//        }
//
//        //二分查找
//        half(arr, 3);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 找寻基准数据的正确索引
            int index = getIndex(arr, low, high);

            // 进行迭代对index之前和之后的数组进行相同的操作使整个数组变成有序
            quickSort(arr, 0, index - 1);
            quickSort(arr, index + 1, high);
        }

    }

    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据 取第一个是基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];

        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        return low; // 返回tmp的正确位置
    }

    static void half(int[] arr,int a){
        int max = arr.length-1;
        int min = 0;
        int half_index = (max + min)/2;
        while (min <= max){
            if(arr[half_index] == a){
                System.out.println(half_index);
                System.out.println(arr[half_index]);
                break;
                /*如果arr[half]>a 说明找的值在中值的左边
                比如a=4 half=(0+5)/2=2 arr[2]=3
                arr[2]<a a在arr[half]右边 所以min=half+1=2+1=3
                然后 half=(3+5)/2=4 arr[4]=5 大与a=4
                max=half-1=3	half=(3+3)/2=half[3]=a 输出后break；
                */
            }else if(arr[half_index] > a){
                //大于a 所以a肯定在arr[half]左边 最大值减一
                //然后 half=(min+max)/2 最新的half范围来找
                max=half_index - 1;
            }else {
                min=half_index + 1;
            }//判断大小后 重新定一个half范围看它的位置
            half_index = (min+max)/2;
        }
    }







}
