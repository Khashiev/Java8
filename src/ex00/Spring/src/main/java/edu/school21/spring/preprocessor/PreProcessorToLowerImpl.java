package edu.school21.spring.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    @Override
    public String preProcess(String input) {
        return input.toLowerCase();
    }
}
