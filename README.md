# VocabTrainer
This trainer bases on the japanese upb course. It will ask you questions about vocabulary. German/English to Japanese. It works inside the console.
## Building the project
Building is done via maven. You will need Java 24
## Execution
Open up console and navigate to the folder that contains the .jar file.
Run as any jar file via 'java -jar VocabTrainer-1.0.jar'. You need to have java installed and set up correctly.
## How to use
Follow the instructions displayed in the console window.
With the current version, you will be instructed to either choose themes or lessons.
By choosing lessons you will then have to specify which ones to use. By choosing themes you will have to specify which theme exactly. As of now, this entails counters.
Then you will be tasked to choose how many questiony you want to be asked. Enter a number. Press enter to use the default value (half of the lesson).
The vocabulary bases on the information available on the panda course (DDMMYYYY 08.07.2025). 
After that you will see:
- English/German so that you must enter the correct Japanese word
- Japanese so that you must enter the correct English/German word
- You can type $help for additional info that may or may not be helpful

Multiple words within one answer will lead to an incorrect evaluation when not all words are correct.
At any point you can exit by typing '$exit'. Typing $exit within a mode will lead you back to the mode selection
