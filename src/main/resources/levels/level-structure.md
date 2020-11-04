# The structure of a level file  

These files are human readable for testing and debugging purposes.  
This file is for reference when writing the parsing methods.  

## Name  
Each file is called ```levelA-B```  where ```A``` is the first level contained in the file, and ```B``` the last one.  

## Content

### Separation  
Each level is enclosed in braces ```{}```.  

### On each line

1. ```{``` then the number of the level, then the size of the board. Optional : the scope of the board that is visible at one time 
Ex : ```{ 1 4 5``` for level 1, of size 4 by 5.   
Ex : ```{ 1 6 30 6 10``` for level 1, of size 6 by 30, but only 6 by 10 is visible at one time

2. the type of the level : ```moves```, ```pieces```, ```time``` or ```unlimited``` respectively refer to a level with limited moves, 
pieces or time.  After a space, write the value of the leftover piece/second/move.

3. the amount of the limited quantity (moves or time in seconds). For pieces write ```none```  

4. the free bonus : ```ChangeBlockColor```, ```EraseBlock```, ```EraseColor```, ```EraseColumn```, ```FreeBee```, 
```FreeBlock```, or ```none```. Then a space followed by the number of bonus available.
If ```none```, don't put a number afterwards. If ```infinite``` append without a space the number of moves necessary to get the next one

5. the objectives to get one star (= win the level) : 
    - start with ```B``` to indicate a number of Bees to save
    - or ```P``` to indicate the score to obtain

6. the objectives to get two stars (same syntax than 5.)  

7. the objectives to get three stars (same syntax than 5.)

8. ```[``` to indicate the beginning of the initial Pieces layout

9. (and more) the initial Pieces on the Board. On each line, write in between parentheses a description of the Piece : 
    - first goes the name of the Piece
    - ```points``` is the number of points this Piece is worth
    - ```isFree``` is either ```true``` or ```false```
    - ```color``` is one of ```red```, ```green```,```yellow```, ```blue```, ```purple``` or ```orange``` 
    - ```visible``` is either ```true``` or ```false``` depending on whether that field of the Board is visible or not  
    
    The syntax is as follows : 
   - ```none``` if no Piece is there and the field is not visible
   - ```visible``` if no Piece is there but the field is visible
   - for a free Bee : ```(Bee points visible)```
   - for a trapped Bee : ```(Bee points color)```
   - for a Bomb : ```(Bomb points isFree)```
   - for a ColorBlock : ```(ColorBlock points isFree color)```
   - for a Decor : ```(Decor pathToIcon)```
   - for a EraseColorBlocks : ```(ErasecolorBlocks points isFree color)```  
    
10. ```]``` to indicate the end of that section

11. the restrictions of Pieces on the level, and their respective probabilities.  
Write each available Piece's name and the probability in percentages without a space. Write ```none``` if not applicable.   
Ex : ```ColorBlock70 Bee20 Bomb10```

12. the restrictions of colors, and their probabilities in percentages. Write ```none``` if not applicable.  
Ex : ```yellow10 red40 green50```