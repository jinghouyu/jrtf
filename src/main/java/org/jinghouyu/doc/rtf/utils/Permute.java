package org.jinghouyu.doc.rtf.utils;
public class Permute{
    // download from http://www.sharejs.com
    static void permute(java.util.List<Integer> arr, int k){
        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            permute(arr, k+1);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1){
            System.out.println(java.util.Arrays.toString(arr.toArray()));
        }
    }
    public static void main(String[] args){
        Permute.permute(java.util.Arrays.asList(1,2,3,4), 0);
    }
}
