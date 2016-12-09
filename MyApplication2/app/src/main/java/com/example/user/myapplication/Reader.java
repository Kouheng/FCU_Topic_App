package com.example.user.myapplication;


import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private String stringmake = "";
    private int intCount = 0;
    private int floatCount = 0;
    private int idCount = 0;
    private int errorCount = 0;
    private boolean errorFlag = false;

    /*主要的邏輯*/
    private void lex(int word) {
        System.out.println("input : " + (char)word  + "      ascii : " + word);           //測試輸入用
        char charWord = (char)word;

        /*判斷接到的東西*/
        switch (charWord) {
            case ';':
                if (!stringNull()) {
                    type();
                }
                //else error();
                break;
            case '\n':
                if(stringNull()) break;
                else error();
                break;
            case 13 :  //換行符號 貌似會出錯用?
                if(stringNull()) break;
                else error();
                break;
            case '\t':
                if(stringNull()) break;
                else error();
                break;
            case ' ':
                if(stringNull()) break;
                else error();
                break;
            case '_':
                if (!stringmake.equals(""))
                    bottomLine();
                else error();
                break;
            case  '.':
                if (!stringmake.equals(""))
                    dot();
                else error();
                break;

            /*沒有保留的特殊字元 底下判斷是英數或錯誤字元*/
            default:
                if (!engAndDig(word)) {
                    error(word);
                    stringmake += charWord;
                }
                else {
                    stringmake += charWord;
                }

                break;

        } //switch
    }//lex()


    /*判斷是否為英文或數字*/
    private boolean engAndDig(int word){
        if (word >=65 && word <=90 );
        else if (word>=97 && word<=122);
        else if (word>=48 && word <=57);
        else return false;
        return true;
    }

    /*NULL for T ,not null for F*/
    public boolean stringNull(){
        if (stringmake.equals(""))
            return true;
        else return false;
    }

    /*節省時間使用正規表達來判斷底線(?)*/
    private void bottomLine(){
        if(!stringmake.matches("[a-zA-Z]" + "([a-zA-Z0-9]*[_]?[a-zA-Z0-9]+)*") )
            error();
        stringmake += "_";
    }

    /*判斷小數點*/
    private void dot(){
        if( !stringmake.matches("[0-9]+") )
            error();
        stringmake += ".";
    }

    /*define token type and print out */
    public void type(){
        System.out.println("\n");
        /*type int*/
        if (errorFlag) {
            System.out.println("ERROR : " + stringmake);
            errorCount++;
        }
        else if (stringmake.matches("[0-9]+"))
        {
            System.out.println("TOKEN_INT : " + stringmake);
            intCount++;
        }

        else if (stringmake.matches("[0-9]+" +".[0-9]+"))
        {
            System.out.println("TOKEN_FLOAT : " +stringmake);
            floatCount++;
        }
        else if (stringmake.matches("[a-zA-Z]" + "([_]?[a-zA-Z0-9]+)*"))
        {
            System.out.println("TOKEN_ID : " + stringmake);
            idCount++;
        }

        else {
            System.out.println("ERROR : " + stringmake);
            errorCount++;
        }


        flagReset();
        System.out.println("-----------------------------");

    }

    /*error and empty the stringMake*/
    public void error(){
        errorFlag = true;
        System.out.println("Error");
    }

    /*error with illegal input*/
    private void error(int word) {
        errorFlag = true;
        System.out.println("Error_illegal_input: " + (char)word);
    }

    /*重置*/
    private void flagReset(){
        errorFlag = false;
        stringmake = "";
    }



    public static void main(String[] args) {
        FileReader fin;
        Reader reader = new Reader();
        try {
            fin = new FileReader("d:\\test.txt");
            int word;

            while (fin.ready()){
                word = fin.read();
                reader.lex(word);
                //System.out.print((char)word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!reader.stringmake.equals(""))
            reader.type();
        System.out.println("\n------EOF------\n");

        System.out.println("intCount : "+reader.intCount);
        System.out.println("floatCount : "+reader.floatCount);
        System.out.println("idCount : "+reader.idCount);
        System.out.println("ErrorToken : "+reader.errorCount);
        System.out.println("\n\n\n\n\n\n");

    }
}
