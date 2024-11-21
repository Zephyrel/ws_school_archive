; Title			CSC384 Assignment 2
; Author:		Russell Ferguson Jr
; Instructor:	Baoqiang Yan
; Date:			9/27/2017

.586
.MODEL FLAT
INCLUDE io.h

.STACK 4096

.DATA
long        DWORD ?
wide        DWORD ?
promptA BYTE    "Please input an integer value for A:", 0
promptB BYTE    "Please input an integer value for B:", 0
promptC BYTE    "Please input an integer value for C:", 0
varA       DWORD	?
varB       DWORD	?
varC       DWORD	?

promptG1	BYTE    "Please input grade1:", 0
promptG2	BYTE    "Please input grade2:", 0
promptG3	BYTE    "Please input grade3:", 0
promptG4	BYTE    "Please input grade4:", 0
grade1			DWORD ?
grade2			DWORD ?
grade3			DWORD ?
grade4			DWORD ?
gradeSum		DWORD ?

string			BYTE	30 DUP(?)
resultLbl		BYTE	"The result is", 0
result1			BYTE	11 DUP(? ), 0
gradeLbl		BYTE	"Results", 0
gradeSumPrompt	BYTE	"Grade Sum: "			; Not null terminated, loading this string will load data after it until null
sumascii		BYTE	11 DUP(? ), 0dh, 0ah	; This has a CR and LF appended to the end for a newline!
gradeAvgPrompt	BYTE	"Grade Average: "
avgascii		BYTE	11 DUP(? ), 0			; This is null terminated! End of Feed!

.CODE

_MainProc PROC
	; part 1, calculate this
	; (a + b * c) / (2 * b)
	; (12 + 2 * 8) / (2 * 2) = (28 / 4)
	input   promptA, string, 30		; get A
	atod    string					; convert to integer
	mov     varA, eax				; store eax value into memory into varA

	input   promptB, string, 30		; get B
	atod    string					; convert to integer
	mov     varB, eax				; store eax value into memory into varB

	input   promptC, string, 30		; get C
	atod    string					; convert to integer
	mov     varC, eax				; store eax value into memory into varB

	; Math time. Calculate 2*b, then b*c, then a + (b*c), then (a+b*c)/(2*b)
	
	; First part of the equation, the dividend
	mov		eax,	varB			; store B into eax
	imul	eax,	varC			; signed multiply eax, which has B in it, by C
	add		eax,	varA			; add A to eax
	mov		long,	eax				; store number in eax, to variable "long"

	; Now the divisor
	mov		eax,	varB			; Second half of the division
	imul	eax,	2				; signed multiply by 2.
	mov		wide,	eax				; store number in eax, to variable "long"

	; Setup for division, then divide
	mov		eax,	long			; Move long into eax
	mov		ebx,	wide			; move wide into ebx
	cdq								; convert doubleword in eax (var long) to quadword in edx:eax

	idiv		ebx					; Signed divide what is found in eax, by the integer in ebx

	dtoa	result1, eax			; Convert eax to ascii, store in sumascii
	output	resultLbl,	result1		; output sum
	
	;******************************************************
	;	Part Two
	;	Grades
	;,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,

	;	example input:	80, 96, 64, 100
	;	sum = 80 + 96 + 64 + 100 + 100 = 440
	;	avg = 440 / 5 = 88

	input   promptG1, string, 30	; get Grade1
	atod    string					; convert to integer
	mov     grade1, eax				; store eax value into memory into varA

	input   promptG2, string, 30	; get Grade2
	atod    string					; convert to integer
	mov     grade2, eax				; store eax value into memory into varB

	input   promptG3, string, 30	; get Grade3
	atod    string					; convert to integer
	mov     grade3, eax				; store eax value into memory into varB

	input   promptG4, string, 30	; get Grade4
	atod    string					; convert to integer
	mov     grade4, eax				; store eax value into memory into varB

	mov		eax,		grade1		; store grade1 into eax
	add		eax,		grade2		; add grade2
	add		eax,		grade3		; add grade3
	add		eax,		grade4		; add grade4
	add		eax,		grade4		; twice, because it's a Final exam grade and worth double
	mov		gradeSum,	eax			; store value in eax, into gradeSum in memory
	dtoa	sumascii,	eax			; convert value in eax to ascii, store in memory as string

	mov		ebx,		5			; move the divisor into ebx
	cdq								; convert the doubleword gradeSum value in eax, to a quadword over edx:eax
	idiv	ebx						; divide eax by ebx to get average

	dtoa	avgascii, eax			; Convert and store the "average" value in eax to avgascii

	; printing out the grades now! Notice that it prints out all of gradeSumPrompt, sumascii, gradeAvgPrompt
	; and avgascii because *only avgascii is a string that is null-terminated with a 0*
	; They're stored right next to each other in memory, so it loads all the bytes, or chars, starting at
	; gradeSumPrompt until it hits a null terminator, aka End Of Feed.

	output	gradeLbl,	gradeSumPrompt	;	print

	mov     eax, 0; exit with return code 0
	ret
_MainProc ENDP
END