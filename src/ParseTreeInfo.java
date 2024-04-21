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
        private String type;
        private int lineno;
        private int column;

        public TypeSpecInfo() {
            this.type = "";
            this.lineno = 1;
            this.column = 1;
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
        private int lineno;
        private int column;
        private ArrayList<ParseTree.Node> children;

        public ProgramInfo() {
            this.lineno = 1;
            this.column = 1;
            this.children = new ArrayList<>();
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

        public ArrayList<ParseTree.Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ParseTree.Node> children) {
            this.children = children;
        }
    }
    public static class FuncDeclInfo
    {
        private String type;
        private String name;
        private int lineno;
        private int column;
        private ArrayList<ParseTree.Node> children;

        public FuncDeclInfo() {
            this.type = "";
            this.name = "";
            this.lineno = 1;
            this.column = 1;
            this.children = new ArrayList<>();
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public ArrayList<ParseTree.Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ParseTree.Node> children) {
            this.children = children;
        }
    }
    public static class ParamInfo
    {
        private String type;
        private String name;
        private int lineno;
        private int column;

        public ParamInfo() {
            this.type = "";
            this.name = "";
            this.lineno = 1;
            this.column = 1;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    public static class LocalDeclInfo
    {
        private String type;
        private String name;
        private int lineno;
        private int column;

        public LocalDeclInfo() {
            this.type = "";
            this.name = "";
            this.lineno = 1;
            this.column = 1;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    public static class StmtStmtInfo
    {
        private int lineno;
        private int column;
        private ArrayList<ParseTree.Node> children;

        public StmtStmtInfo() {
            this.lineno = 1;
            this.column = 1;
            this.children = new ArrayList<>();
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

        public ArrayList<ParseTree.Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ParseTree.Node> children) {
            this.children = children;
        }
    }
    public static class ArgInfo
    {
        private int lineno;
        private int column;
        private ArrayList<ParseTree.Node> children;

        public ArgInfo() {
            this.lineno = 1;
            this.column = 1;
            this.children = new ArrayList<>();
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

        public ArrayList<ParseTree.Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ParseTree.Node> children) {
            this.children = children;
        }
    }
    public static class ExprInfo {
        private String type;
        private int lineno;
        private int column;
        private ArrayList<ParseTree.Node> children;

        public ExprInfo() {
            this.type = "";
            this.lineno = 1;
            this.column = 1;
            this.children = new ArrayList<>();
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

        public ArrayList<ParseTree.Node> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<ParseTree.Node> children) {
            this.children = children;
        }
    }

}
