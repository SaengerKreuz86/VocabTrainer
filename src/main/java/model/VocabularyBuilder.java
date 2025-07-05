package model;

import java.util.List;

public class VocabularyBuilder {
    private final Vocabulary vocabulary;

    public VocabularyBuilder(){
        vocabulary = new Vocabulary(null, null);
    }

    public Vocabulary build(){
        if (vocabulary.getJapanese() == null || vocabulary.getEnglishGerman() == null){
            throw new IllegalStateException("Vocabulary was initialized wrong");
        }else{
            return vocabulary;
        }
    }

    public VocabularyBuilder japanese(String... meanings){
        vocabulary.setJapanese(List.of(meanings));
        return this;
    }

    public VocabularyBuilder englishGerman(String... meanings){
        vocabulary.setEnglishGerman(List.of(meanings));
        return this;
    }

    public Vocabulary buildSingleJapanese(String jap, String... meanings){
        return this.japanese(jap).englishGerman(meanings).build();
    }
    public Vocabulary buildDoubleJapanese(String jap1, String jap2, String... meanings){
        return this.japanese(jap1, jap2).englishGerman(meanings).build();
    }
}
