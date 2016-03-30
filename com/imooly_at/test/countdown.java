package com.imooly_at.test;

/**
 * Created by Administrator on 2015/6/4.
 */
public class countdown {

    void cd()
    {
        for (int i=99;i>=0;i--)
        {
            System.out.println(i);
            System.out.print("i");
        }

    }

     static void xxx()
    {
        for (int n=1;n<=10;n++)
        {
            if(n==5)
            {System.out.println(n);}
            else
            {
            System.out.println("I'm not 5");
            }
        }
    }
    static void test() {

            int x = 3;
            while ( x > 0) {
                System.out.println("WHILE BEGIN x = "+x);
                if (x > 2){
                    System.out.print("a");
                }
                x = x - 1;
                System.out.print("-");

                if (x == 2) {
                    System.out.print("b c");}


                if (x == 1) {
                    System.out.println ("begin if");
                    System.out.print("d");
                    x = x - 1;}
            }


    }

    public static void main(String[] args)
    {
        test();


    }


}
