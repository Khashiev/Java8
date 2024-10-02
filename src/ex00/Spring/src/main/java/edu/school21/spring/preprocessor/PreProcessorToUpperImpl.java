package edu.school21.spring.preprocessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String preProcess(String input) {
        return input.toUpperCase();
    }
}
