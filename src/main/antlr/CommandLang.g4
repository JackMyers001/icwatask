grammar CommandLang;

program
   : command* EOF
   ;

command
   : placeCommand
   | moveCommand
   | reportCommand
   | leftCommand
   | rightCommand
   ;

placeCommand
   : PLACE INT COMMA INT COMMA direction
   ;

moveCommand
   : MOVE
   ;

reportCommand
   : REPORT
   ;

leftCommand
   : LEFT
   ;

rightCommand
   : RIGHT
   ;

direction
   : NORTH
   | SOUTH
   | EAST
   | WEST
   ;

PLACE
   : 'PLACE'
   ;

MOVE
   : 'MOVE'
   ;

REPORT
   : 'REPORT'
   ;

LEFT
   : 'LEFT'
   ;

RIGHT
   : 'RIGHT'
   ;

NORTH
   : 'NORTH'
   ;

SOUTH
   : 'SOUTH'
   ;

EAST
   : 'EAST'
   ;

WEST
   : 'WEST'
   ;

INT
   : [0-9]+
   ;

COMMA
   : ','
   ;
   // Skip whitespace
   
WS
   : [ \t\r\n]+ -> skip
   ;

