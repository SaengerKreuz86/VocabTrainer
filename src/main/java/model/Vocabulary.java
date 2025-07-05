package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Vocabulary {
    private List<String> japanese;
    private List<String> englishGerman;
}
