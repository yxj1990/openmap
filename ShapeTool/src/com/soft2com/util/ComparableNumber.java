package com.soft2com.util;

import java.lang.Number;
/**
 * 可比较数字
 */

public class ComparableNumber implements Comparable
{
    private double num=0.0;
    /*--*************************构造函数******************************--*/
    public ComparableNumber(int i){
        this.num=i;
    }
    public ComparableNumber(double d){
        this.num=d;
    }
    public ComparableNumber(float f){
        this.num=f;
    }
    /**
     * 返回本类中所包含的数字
     */
    public double getNumber(){
        return this.num;
    }
    public int compareTo(Comparable comparableNumber)
    {
        return (int)(this.num-((ComparableNumber)comparableNumber).getNumber());
    }
    public String toString(){
        return String.valueOf(this.num);
    }
}