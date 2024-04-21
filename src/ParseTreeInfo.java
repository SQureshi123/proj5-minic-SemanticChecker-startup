/////////////////////////////////////////////////////////////////////////////////////////
//  MIT License                                                                        //
//                                                                                     //
//  Copyright (c) 2024 Hyuntae Na                                                      //
//                                                                                     //
//  Permission is hereby granted, free of charge, to any person obtaining a copy       //
//  of this software and associated documentation files (the "Software"), to deal      //
//  in the Software without restriction, including without limitation the rights       //
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell          //
//  copies of the Software, and to permit persons to whom the Software is              //
//  furnished to do so, subject to the following conditions:                           //
//                                                                                     //
//  The above copyright notice and this permission notice shall be included in all     //
//  copies or substantial portions of the Software.                                    //
//                                                                                     //
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR         //
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,           //
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE        //
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER             //
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,      //
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE      //
//  SOFTWARE.                                                                          //
/////////////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ParseTreeInfo
{
    // Use this classes to store information into parse tree node (subclasses of ParseTree.Node)
    // You should not modify ParseTree.java
    public static class TypeSpecInfo
    {
        public int lineno;
        public int column;
        public String type;

        public TypeSpecInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLineno() {
            return lineno;
        }

        public void setLineno(int lineno) {
            this.lineno = lineno;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }
    }
    public static class ProgramInfo
    {
        public int lineno;
        public int column;
        public String type;

        public ProgramInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }
    }
    public static class FuncDeclInfo
    {
        public int lineno;
        public int column;
        public String type;

        public FuncDeclInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }
    }
    public static class ParamInfo
    {
        public int lineno;
        public int column;
        public String type;

        public ParamInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }
    }
    public static class LocalDeclInfo
    {
        public int lineno;
        public int column;
        public String type;

        public LocalDeclInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }
    }
    public static class StmtStmtInfo
    {
        public int lineno;
        public int column;
        public String type;

        public StmtStmtInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }
    }
    public static class ArgInfo
    {
        private int lineno;
        private int column;
        private String valueType;

       // public ArgInfo(int lineno, int column, String valueType){
       //     this.lineno = lineno;
       //     this.column = column;
       //     this.valueType = valueType;

       // }
        public int getLineno(){
            return lineno;
        }
        public int getColumn(){
            return column;
        }
        public String getValueType(){
            return valueType;
        }

    }
    public static class ExprInfo
    {
        public int lineno;
        public int column;
        public String type;

        public ExprInfo() {
            this.lineno = -1;
            this.column = -1;
            this.type = "";
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLineno() {
            return lineno;
        }

        public void setLineno(int lineno) {
            this.lineno = lineno;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }
    }



}
