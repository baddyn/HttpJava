package org.example;

public class Maths {

    int add(int x, int y){
        return x+y;
    }

    int arraySum(int[] arr) {
        int ans=0;
        for(int i=0;i<arr.length;i++)
            ans+=arr[i];
        return ans;
    }

    boolean positiveCheck(int x){
        return x>=0;
    }

    int division(int x, int y) {

        try {
            int z = x/y;
            return z;
        }
        catch(ArithmeticException ex){
            throw ex;
        }
    }

    int[] arrayReturn(int[] arr){
        if(arr.length ==0)
            return null;
        return arr;
    }
}
