# VocabTrainer
This trainer will ask you questions about vocabulary. German/English to Japanese
## Building the project
Building is done via maven. You will need Java 24
## Execution
Open up console and navigate to the folder that contains the .jar file.
Run as any jar file via 'java -jar VocabTrainer-1.0.jar'. You need to have java installed and set up correctly.
## How to use
Follow the instructions displayed in the console window.
With the current version, you will be instructed to enter the lesson you want to practice. The lesson corresponds to the lesson from the Panda course (DDMMYYY 04.06.2025).
Lesson 0 are vocabularies that were picked up during the lesson and don't belong in any lesson.
Then you will be tasked to choose how many questiony you want to be asked. Enter a number. Press enter to use the default value (half of the lesson).
After that you will see:
- English/German so that you must enter the correct Japanese word
- Japanese so that you must enter the correct English/German word

Multiple words within one answer will lead to an incorrect evaluation when not all words are correct.
At any point you can exit by typing '$exit'.
