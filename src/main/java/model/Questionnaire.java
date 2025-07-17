package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questionnaire {
    private List<Vocabulary> vocabularies = new ArrayList<>();
    private String infoText = "Only god can help you.\r\n";
}
