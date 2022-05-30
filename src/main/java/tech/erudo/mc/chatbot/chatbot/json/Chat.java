package tech.erudo.mc.chatbot.chatbot.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    private String commandId;
    private String commandName;
    private String text;
    private String type;
    private double mood;
    private double negaposi;
    private List<NegaposiList> negaposiList;
    private List<Emotion> emotion;
    private List<EmotionList> emotionList;
    private List<WordList> wordList;
    private String art;
    private String org;
    private String psn;
    private String loc;
    private String dat;
    private String tim;

    class NegaposiList {
        private String word;
        private int score;
    }

    class Emotion {
        private int angerFear;
        private int joySad;
        private int likeDislike;
    }

    class EmotionList {
        private String word;
        private int angerFear;
        private int joySad;
        private int likeDislike;
    }

    class WordList {
        private String feature;
        private int start;
        private String surface;
    }

}
