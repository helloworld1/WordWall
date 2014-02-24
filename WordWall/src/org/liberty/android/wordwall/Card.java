package org.liberty.android.wordwall;

public class Card {

    private final String question;

    private final String answer;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Card [question=" + question + ", answer=" + answer + "]";
    }
}
