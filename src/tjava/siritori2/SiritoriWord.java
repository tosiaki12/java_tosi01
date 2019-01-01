package tjava.siritori2;

public class SiritoriWord extends WordTemplate{

    protected String fWord;
    protected String sWord;
    protected String lWord;

    protected int oldId;

    protected boolean isNew;


    public SiritoriWord(String fWord,String sWord,String lWord,boolean isNew){
        this.title = "SiritoriWord";
        this.sub = "NOTHING";
        this.fWord = fWord;
        this.sWord = sWord;
        this.lWord = lWord;

        this.isNew = isNew;
        this.compFromTo(isNew);

        this.checkThisNN();


    }


    public void compFromTo(boolean isNew){

        if(isNew == true){
            this.from = "user";
            this.to = "db";
        } else {
            this.from = "db";
            this.to = "user";
        }

    }

    public boolean checkThisNN(){
        boolean isNN = false;
        if(fWord.equals("ん") || lWord.equals("ん")){
            isNN = true;
            throw new InvalidWordException("ん",this.getWord());
        } else {
            isNN = false;
        }
        return isNN;
    }

    public String getWord(){
        StringBuilder conpWordBuilder = new StringBuilder();
        conpWordBuilder.append(this.fWord+" : "+this.sWord+" : "+this.lWord);
        String compWord = conpWordBuilder.toString();
        return compWord;
    }




    public String[] setArray(){
        String[] str = new String[] {this.fWord,this.sWord,this.lWord};
        return str;
    }


    public void setFWord(String fWord){
        this.checkThisNN();
        this.fWord = fWord;
    }

    public String getFWord(){
        return this.fWord;
    }



    public void setSWord(String sWord){
        this.checkThisNN();
        this.sWord = sWord;
    }

    public String getSWord(){
        return this.sWord;
    }



    public void setLWord(String lWord){
        this.checkThisNN();
        this.lWord = lWord;
    }


    public String getLWord(){
        return this.lWord;
    }



    public void setFromTo(String from,String to){
        this.from = from;
        this.to = to;
    }

    public String[] getFromTo(){
        String[] fromTo = new String[] {(String) from,(String) to};

        return fromTo;
    }




}
