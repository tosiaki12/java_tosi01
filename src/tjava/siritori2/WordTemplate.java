package tjava.siritori2;

public abstract class WordTemplate {

    protected String title;
    protected String word;
    protected String sub;
    protected int id;
    protected Object other;
    protected Object from;
    protected Object to;



    protected void setTitle(String title){
        this.title = title;
    }

    protected String getTitle(){
        return this.title;
    }


    protected void setWord(String word){
        this.word = word;
    }

    protected String getWord(){
        return this.word;
    }


    protected void setSub(String sub){
    	this.sub = sub;
    }

    protected String getSub(){
        return this.sub;
    }


    protected void setId(int id){
    	this.id = id;
    }
    protected int getId(){
        return this.id;
    }


    protected void setOther(Object other){
    	this.other = other;
    }
    protected Object getOther(){
        return this.other;
    }


    protected void setFrom(Object from){
    	this.from = from;
    }
    protected Object getFrom(){
        return this.from;
    }


    protected void setTo(Object to){
    	this.to = to;
    }

    protected Object getTo(){
        return this.to;
    }


}
