package com.lvjq.blog.pojo;

import lombok.Data;

@Data
public class TransWord {

    private String word;

    private String result;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
