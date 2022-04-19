package Lab2;

import Lab2.Instruction;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class mips {
    
    /****************************************************
     * Class is to organize data and simplify process of 
     * converting mips assembly program into binary.
     ****************************************************/

    //---MEMBERS---

        //file io
        public static String filename; //assembly file name
        public static File asmFile; //assembly file

        //look up tables
        public static HashMap<String, String>  type = new HashMap<>(); 
        public static HashMap<String, Integer> opcode = new HashMap<>();
        public static HashMap<String, Integer> func = new HashMap<>();
        public static HashMap<String, Integer> reg = new HashMap<>();
        public static HashMap<String, Integer> lineLabel = new HashMap<>();

        //data
        public static ArrayList<String> lines; //holds file instruction lines as strings
        public static ArrayList<Instruction> program; //holds file instructions as parsed data
        public static ArrayList<Integer> bin; //holds file conversion to binary

    //---METHODS---
        //constructors
        public mips(){
            //initialize filename with nothing
            filename = "";
            asmFile = null;

            //allocate members
            type = new HashMap<>(); 
            opcode = new HashMap<>();
            func = new HashMap<>();
            reg = new HashMap<>();
            lineLabel = new HashMap<>();

            lines = new ArrayList<>();
            program = new ArrayList<>();

            //initialize tables
            init_tables();

        }

        //init
        public static void init_tables()
        {
            //calls all init functions to fill out look up tables
            init_type(type);
            init_opMap(opcode);
            init_func(func);
            init_reg(reg);
        }
        
        public static void init_type(HashMap<String, String> typeMap) {
            // initialize instruction type hashmap
            // instructions are R, I, or J type
            typeMap.put("add", "R");
            typeMap.put("addi", "I");
            typeMap.put("addiu", "I");
            typeMap.put("addu", "R");
            typeMap.put("and", "R");
            typeMap.put("andi", "I");
            typeMap.put("beq", "I");
            typeMap.put("bne", "I");
            typeMap.put("j", "J");
            typeMap.put("jal", "J");
            typeMap.put("jr", "R");
            typeMap.put("lbu", "I");
            typeMap.put("lhu", "I");
            typeMap.put("ll", "I");
            typeMap.put("lui", "I");
            typeMap.put("lw", "I");
            typeMap.put("nor", "R");
            typeMap.put("or", "R");
            typeMap.put("ori", "I");
            typeMap.put("slt", "R");
            typeMap.put("slti", "I");
            typeMap.put("sltiu", "I");
            typeMap.put("sltu", "R");
            typeMap.put("sll", "R");
            typeMap.put("srl", "R");
            typeMap.put("sb", "I");
            typeMap.put("sc", "I");
            typeMap.put("sh", "I");
            typeMap.put("sw", "I");
            typeMap.put("sub", "R");
            typeMap.put("subu", "R");

        }

        public static void init_opMap(HashMap<String, Integer> opMap) {
            // initialize opcode hashmap
            // opcodes are 6 bits (31:26)
            opMap.put("add", 0x0);
            opMap.put("addi", 0x8);
            opMap.put("addiu", 0x9);
            opMap.put("addu", 0x0);
            opMap.put("and", 0x0);
            opMap.put("andi", 0xc);
            opMap.put("beq", 0x4);
            opMap.put("bne", 0x5);
            opMap.put("j", 0x2);
            opMap.put("jal", 0x3);
            opMap.put("jr", 0x0);
            opMap.put("lbu", 0x24);
            opMap.put("lhu", 0x25);
            opMap.put("ll", 0x30);
            opMap.put("lui", 0xf);
            opMap.put("lw", 0x23);
            opMap.put("nor", 0x0);
            opMap.put("or", 0x0);
            opMap.put("ori", 0xd);
            opMap.put("slt", 0x0);
            opMap.put("slti", 0xa);
            opMap.put("sltiu", 0xb);
            opMap.put("sltu", 0x0);
            opMap.put("sll", 0x0);
            opMap.put("srl", 0x0);
            opMap.put("sb", 0x28);
            opMap.put("sc", 0x38);
            opMap.put("sh", 0x29);
            opMap.put("sw", 0x2b);
            opMap.put("sub", 0x0);
            opMap.put("subu", 0x0);

        }

        public static void init_func(HashMap<String, Integer> funcMap) {
            // initialize function address hashmap
            // registers are 6 bits (5:0)
            funcMap.put("add", 0x20);
            funcMap.put("addu", 0x21);
            funcMap.put("and", 0x24);
            funcMap.put("jr", 0x08);
            funcMap.put("nor", 0x27);
            funcMap.put("or", 0x25);
            funcMap.put("slt", 0x2a);
            funcMap.put("sltu", 0x2b);
            funcMap.put("sll", 0x00);
            funcMap.put("srl", 0x02);
            funcMap.put("sub", 0x22);
            funcMap.put("subu", 0x23);
        }

        public static void init_reg(HashMap<String, Integer> regMap) {
            // initialize register address hashmap
            // registers are from 0-31
            regMap.put("$zero", 0);
            regMap.put("$0", 0);
            regMap.put("$at", 1);
            regMap.put("$v0", 2);
            regMap.put("$v1", 3);
            regMap.put("$a0", 4);
            regMap.put("$a1", 5);
            regMap.put("$a2", 6);
            regMap.put("$a3", 7);
            regMap.put("$t0", 8);
            regMap.put("$t1", 9);
            regMap.put("$t2", 10);
            regMap.put("$t3", 11);
            regMap.put("$t4", 12);
            regMap.put("$t5", 13);
            regMap.put("$t6", 14);
            regMap.put("$t7", 15);
            regMap.put("$s0", 16);
            regMap.put("$s1", 17);
            regMap.put("$s2", 18);
            regMap.put("$s3", 19);
            regMap.put("$s4", 20);
            regMap.put("$s5", 21);
            regMap.put("$s6", 22);
            regMap.put("$s7", 23);
            regMap.put("$t8", 24);
            regMap.put("$t9", 25);
            regMap.put("$k0", 26);
            regMap.put("$k1", 27);
            regMap.put("$gp", 28);
            regMap.put("$sp", 29);
            regMap.put("$fp", 30);
            regMap.put("$ra", 31);
        }

        //file io
        public static ArrayList firstpass(String file) {
            // takes filename as input
            // will read file line by line
            // passes line to comment filtering function
            // creates list of lines without comments
            // consider: linenums will be one off, won't need to traverse file again
            try {
                filename = file;
                asmFile = new File(filename);
                Scanner fileread = new Scanner(asmFile);
                
                while (fileread.hasNextLine()) {
                    // place where parse passes through
                    String line = fileread.nextLine();
                    // removes comments from code
                    line = filtercomments(line);
                    // adding filtered line to list
                    lines.add(line);
                }

                //close file
                fileread.close();
                // return array of lines
                return lines;

            } catch (FileNotFoundException e) {
                System.out.println("File was not found");
            }
            return null;
        }
        
        public static void secondpass() {
            // takes arraylist of lines in file as input
            // will read each index and check if it is a label
            // would we need to account for jumps?
            for (int i = 0; i < lines.size(); i++) {
                String line = (String) lines.get(i);
                if (line.contains(":")) {
                    label(line, i + 1);
                }
            }
            // try {
            // int linenum = 1;
            // File myfile = new File(filename);
            // Scanner fileread = new Scanner(myfile);
            // while (fileread.hasNextLine()) {
            // // place where parse passes through
            // String line = fileread.nextLine();
            // label(line, linenum);
            // linenum++;
            // }
            // fileread.close();
            // } catch (FileNotFoundException e) {
            // System.out.println("File was not found");
            // }
        }

        public static void thirdpass() {
            // takes filename as input
            // will read file line by line
            // passes line to line filtering function
            String test[];
            Instruction temp = new Instruction();
            for (int i = 0; i < lines.size(); i++) {
                String line = (String) lines.get(i);
                // check if j/jal instruction
                if (!line.contains("$")) {
                    line = line.trim();
                    test = line.split(" ");
                    
                    //store jump instruction 
                    temp.type = "J";
                    temp.instruct = test[0];
                    //more jump stuff


                } else {
                    // remove comments and whitespace from line
                    line = filter(line);
                    // strip whitespace
                    line = line.replaceAll("\\s", "");
                    // test to make sure line is properly filtered at this point
                    test = line.split("\\$");
                    if (line != "") {
                        System.out.println(line);
                    }
                }
            }
        }

        public static void label(String line, int linenum) {
            // finds line or lines that contain labels
            // will have these two numbers into hashmap upon ...
            if (line.contains(":")) {
                System.out.println(line);
                System.out.println(linenum);
            }
        }

        public static String filtercomments(String line) {
            //takes line and filters out comments
            String filtered = "";
            for (int i = 0; i < line.length(); i++) {
                String x = Character.toString(line.charAt(i));
                if (!x.contains("#")) {
                    filtered += Character.toString(line.charAt(i));
                } else {
                    return filtered;
                }
            }
            return filtered;
        }

        public static String filter(String line) {
            //takes line and filters out whitespace
            String filtered = "";
            for (int i = 0; i < line.length(); i++) {
                String x = Character.toString(line.charAt(i));
                if (!x.contains("\\s+") && !x.contains(" ") && !x.contains("\n")) {
                    filtered += Character.toString(line.charAt(i));
                }
            }
            return filtered;
        }

        //data io
        public static void printBinary(){
            //loop through binary list and print asm binary
            for(int i = 0; i < bin.size(); i++)
            {
                System.out.print(bin.get(i)); 
            }
        }

        //data manip
        public static int inst2bin(int instNum) {
            //take instruction from list and convert it to binary
            int binary = 0;

            if (program.get(instNum).type == "R") {
                // type R instruction
                binary |= ((program.get(instNum).opcode & 63) << 26); // 31-26
                binary |= ((program.get(instNum).rs & 31) << 21); // 25-21
                binary |= ((program.get(instNum).rt & 31) << 16); // 20-16
                binary |= ((program.get(instNum).rd & 31) << 11); // 15-11
                binary |= ((program.get(instNum).shamt & 31) << 6); // 10-6
                binary |= ((program.get(instNum).func & 63) << 0); // 5-0
            } else if (program.get(instNum).type == "I") {
                // type I instruction
                binary |= ((program.get(instNum).opcode & 63) << 26); // 31-26
                binary |= ((program.get(instNum).rs & 31) << 21); // 25-21
                binary |= ((program.get(instNum).rt & 31) << 16); // 20-16
                binary |= ((program.get(instNum).immediate & 0xFFFF) << 0); // 15-0
            } else if (program.get(instNum).type == "J") {
                // type J instruction
                binary |= ((program.get(instNum).opcode & 63) << 26); // 31-26
                binary |= ((program.get(instNum).address & 31) << 0); // 25-0

            }

            return binary;
        }

        public static void prog2bin(){
            //program will iterate through size of program and
            //generate line by line binary 
            for(int i = 0; i < program.size(); i++)
            {
                bin.add(inst2bin(i));
            }
        }
        
}

