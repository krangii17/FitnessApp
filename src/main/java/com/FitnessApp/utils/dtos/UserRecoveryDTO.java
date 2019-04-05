package com.FitnessApp.utils.dtos;

public class UserRecoveryDTO {
    private String secretQuestion;

    public UserRecoveryDTO() {
    }

    public UserRecoveryDTO(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }
}
