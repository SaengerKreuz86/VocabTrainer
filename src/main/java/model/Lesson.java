package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Lesson {
    private final int id;
    private final Map<List<String>, List<String>> vocabularies;
}
