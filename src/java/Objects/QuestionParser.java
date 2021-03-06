package Objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class QuestionParser {
    BufferedReader sc;
    String folder, file;
    
    public QuestionParser(){
        folder = "c:\\selftest\\selftest11e\\";
        file = "chapter1.txt";
        String path = folder + file;
        try{
            sc = new BufferedReader(new InputStreamReader(new FileInputStream(path), "Cp1252"));
        } catch (Exception e){
            System.out.println("The file could not be found");
        }
    }
    
    public QuestionParser(String file){
        folder = "c:\\selftest\\selftest11e\\";
        this.file = file;
        String path = folder + file;
        try{
            sc = new BufferedReader(new InputStreamReader(new FileInputStream(path), "Cp1252"));
        } catch (Exception e){
            System.out.println("The file could not be found");
        }
    }
    
    public QuestionParser(String folder, String file){
        this.folder = folder;
        this.file = file;
        String path = folder + file;
        try{
            sc = new BufferedReader(new InputStreamReader(new FileInputStream(path), "Cp1252"));
        } catch (Exception e){
            System.out.println("The file could not be found");
        }
    }
    
    public void print(){
        String s = "";
        try{
            while ((s = sc.readLine()) != null){
            System.out.println(s);
            }
        } catch (Exception e){
            System.out.println("");
        }
    }
    
    public ArrayList<Question> allQuestions() throws IOException{
        ArrayList<Question> ques = new ArrayList<>();
        String s = sc.readLine();
        int chapter = 0, question = 1;
        
        //First line - set Chapter #
                String t = s.substring(s.indexOf(' ')).trim();
                String u = "";
                while(t.charAt(0) != ' '){
                    u = u + t.charAt(0);
                    t = t.substring(1);

                }
                chapter = Integer.parseInt(u);
                s = sc.readLine();
            
        while ((s = sc.readLine()) != null){
            ques.add(oneQuestion(s, chapter, question));
            question++;
        }
        
        return ques;
    }
    
    private Question oneQuestion (String s, int chapter, int question) throws IOException{
        Question q = new Question();
        
        //Set Chapter and Question #
        q.setChapter(chapter);
        q.setQuestion(question);
        while (!s.startsWith("#")){
            
            
            
            //Set text for question
            if (!s.isEmpty() && !isNumeric(s.charAt(0))){
                s = setQuestionText(q, s);
            }
            
            //Set text for answer choices
            if (!s.isEmpty() && isChoice(s.substring(0, 2))){
                char a = s.charAt(0);
                try{
                s = s.substring(s.indexOf("\t"));
                
                } catch(Exception e){
                s = s.substring(s.indexOf(" "));
                }

                s = s.trim();
                
                switch(a){
                    case 'a': q.setChoiceA(s); break;
                    case 'b': q.setChoiceB(s); break;
                    case 'c': q.setChoiceC(s); break;
                    case 'd': q.setChoiceD(s); break;
                    case 'e': q.setChoiceE(s); break;
                    default: break;
                }
            }
            
            //Set answer key and hint
            if (!s.isEmpty() && s.toUpperCase().startsWith("KEY:")){
                String t = s.substring(4);
                String answers = "";
                while(!t.isEmpty() && (t.charAt(0) != ' ')){
                    answers = answers + t.charAt(0); //Add each individual answer to the answwer key
                    t = t.substring(1);
                }
                q.setAnswerKey(answers);
                
                t = t.trim();
                if (!t.isEmpty()){
                    q.setHint(t); // If any more words remain, they are the hint
                }
            }
            if (!s.equals("#")){
                try{
                    s = sc.readLine();
                    s = s.trim();
                } catch (Exception e){
                    break;
                }
            }
        }
        
        return q;
    }
    
    public boolean isNumeric(char a){
        if ((a >= 'a' && a <= 'z' ) || (a >= 'A' && a <= 'Z' )){
            return true;
        }
        
        return false;
    }
    
    public boolean isChoice(String s){
        return (s.equals("a.") || s.equals("b.") || s.equals("c.") ||s.equals("d.") || s.equals("e."));
    }
    
    public String setQuestionText(Question q, String s) throws IOException {
        //Remove number from beginning of string
        try{
            s = s.substring(s.indexOf("\t"));
                
            } catch(Exception e){
            s = s.substring(s.indexOf(" "));
            }
        
        s = s.trim(); // Remove leading whitespace
        
        String set = s; // First line of question
        s = sc.readLine();
        
        while (!s.startsWith("a.")){ // Add subsequent lines to question text
            set = set + "\n" + s;
            s = sc.readLine();

        }
        
        q.setText(set); //Set the text in Question class
        return s; // Return altered string
    }
}
