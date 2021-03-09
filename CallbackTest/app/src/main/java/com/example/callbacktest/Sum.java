package com.example.callbacktest;

public class Sum {

    interface OnMaxNumberCb {
        void onMaxNumber(int number, int exceed);
    }

    private int number = 0;
    private int maxNumber = 0;
    private OnMaxNumberCb myCallback;

    public void setOnMaxNumberCb(OnMaxNumberCb callback) {
        myCallback = callback;
        // 위의 코드와 같은 코드
        // this는 생성자의 parameter(매개변수)와 인스턴스 변수가 이름이 같을때 사용
//        this.myCallback = callback;
    }

    public void setMaxNumber(int max) {
        maxNumber = max;
    }

    public int addNumber(int adder) {
        number = number + adder;

        if (myCallback != null) {
            if (number >= maxNumber) {
                myCallback.onMaxNumber(number,number-maxNumber);
                System.out.println("sum");
            }
        }
        return number;
    }

    public  int getTotal() {
        return number;
    }
}
