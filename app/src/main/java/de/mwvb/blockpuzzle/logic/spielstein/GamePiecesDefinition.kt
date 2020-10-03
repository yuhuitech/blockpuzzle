package de.mwvb.blockpuzzle.logic.spielstein

// Kotlin supports multi line strings
class GamePiecesDefinition {
    val gamePieces =
"""
// Jede Spielsteinart standardmäßig 4x dabei.
// Je nach Schwierigkeitsgrad wird das zum Teil abhängig von der Punktzahl variiert.

#1
n=3
.....
.....
..1..
.....
.....

#2
n=2
R=1
.....
.....
.11..
.....
.....
   
#3
n=2
R=2
.....
.....
.111.
.....
.....

#4
n=2
R=2
.....
.....
1111.
.....
.....

#5
n=2
R=1
.....
.....
11111
.....
.....

#Ecke2
R=1
RR=1
L=1
.....
.1...
.11..
.....
.....

#Ecke3
R=1
RR=1
L=1
.....
.1...
.1...
.111.
.....

#2x2
.....
.11..
.11..
.....
.....

#3x3
.....
.111.
.111.
.111.
.....


// Game pieces for more difficulty ----

#J
min=1000
.....
.1...
.111.
.....
.....

#L
min=1000
.....
.....
...1.
.111.
.....

#2x2_Bonus:2x2
min=2000

#2x3
min=3000
.....
.111.
.111.
.....
.....

#2x3_Bonus:2x3
min=4000

#S
min=5000
.....
.....
..11.
.11..
.....

#Z
min=5000
.....
.....
.11..
..11.
.....

#3x3_Bonus1:3x3
min=6000

#3x3_Bonus2:3x3
min=7000

#T
R=1
RR=1
L=1
min=8000
.....
..1..
.111.
.....
.....

#3x3_Bonus3:3x3
min=9000

#4_Bonus:4
min=10000

#Ecke3_Bonus1:Ecke3
min=11000

#2Dots
min=12000
.....
.1...
..1..
.....
.....

#X
min=13000
.....
.1.1.
..1..
.1.1.
.....

#1_Bonus:1
n=2
min=15000

#Ecke3_Bonus2:Ecke3
min=20000

#Slash
min=30000
.....
...1.
..1..
.1...
.....

#3x3_Bonus4:3x3
min=40000

#BigSlash
min=50000
....1
...1.
..1..
.1...
1....

#X_Bonus:X
min=60000


"""

}