; CSC382	Assignment Three
; Author:	Russell Ferguson Jr
; Date:		10 / 2017

.586
.MODEL FLAT
INCLUDE io.h
.STACK 4096


.DATA

; Variables

lowercaseCount	DWORD	0
uppercaseCount	DWORD	0
digitCount		DWORD	0
spaceCount		DWORD	0
totalCount		DWORD	0

; Prompts

prompt1 BYTE    "Enter a String: ", 0

string  BYTE    50 DUP(? )

outputLbl BYTE	"Results", 0
totalLbl BYTE	"Total number of characters: "
totalascii	BYTE 11 DUP(' '), 0dh, 0ah
lowercLbl	BYTE "Number of lowercase Letters in the input: "
lowerascii	BYTE 11 DUP(' '), 0dh, 0ah
uppercLbl	BYTE "Number of uppercase Letters in the input: "
upperascii	BYTE 11 DUP(' '), 0dh, 0ah
digitLbl	BYTE "Number of digits in the input: "
digitascii	BYTE 11 DUP(' '), 0dh, 0ah
spaceLbl	BYTE "Number of spaces in the input: "
spaceascii	BYTE 11 DUP(' '), 0



.CODE
_MainProc PROC


		input   prompt1,	string,	50	; read ASCII characters
		lea		esi,		string

		; write your loop logic here and store the results in the different count variables
		; Space = 0x20h
		; Digits 0x30h "0" -> 0x39h "9"
		; Upper case 0x41h "A" -> 0x5A "Z"
		; Lower case 0x61h "a" -> 0x7A "z"

		;

		mov		eax,		0
		mov		ecx,		0
		;begin Main Loop
	L1:	mov		al,		byte ptr [esi+ecx]
		cmp		al,		00h		; compare to null, ends loop
		je		L12
		cmp		al,		20h		; compare to space
		je		L7
		cmp		al,		30h		; digits, compare to "0"
		jae		L4
	L2:	cmp		al,		41h		; Upper case, compare to "A"
		jae		L5
	L3:	cmp		al,		61h		; Lower case, compare to "a"
		jae		L6
		jmp		L11
	
	
	L4:	cmp		al,		39h		; digits, compare to upper value
		ja		L2
		jmp		L8
		

	L5: cmp		al,		5Ah		; Upper case, compare to upper value
		ja		L3		
		jmp		L9
	
	L6: cmp		al,		7Ah		; Lower case, compare to upper value
		ja		L11	
		jmp		L10

	L7:	mov		eax,		spaceCount	; increase spaceCount
		add		eax,		1
		mov		spaceCount,	eax
		jmp		L11

	L8:	mov		eax,		digitCount	; update digitCount
		add		eax,		1
		mov		digitCount,	eax
		jmp		L11
	L9: mov	eax,		uppercaseCount	; update uppercaseCount
		add		eax,		1
		mov		uppercaseCount,	eax
		jmp		L11
	L10: mov	eax,		lowercaseCount	; update lowercaseCount
		add		eax,		1
		mov		lowercaseCount,	eax

	L11: mov	eax,		totalCount	; update totalCount
		add	eax,			1
		mov	totalCount,		eax
		add	ecx,			1
		jmp	L1
	
	L12: mov	eax,		totalCount	; end of loop
		dtoa	totalascii,	eax
		mov		eax,		lowercaseCount
		dtoa	lowerascii,	eax
		mov		eax,		uppercaseCount
		dtoa	upperascii,	eax
		mov		eax,		digitCount
		dtoa	digitascii,	eax
		mov		eax,		spaceCount
		dtoa	spaceascii,	eax
		
		
		output	outputLbl,	totalLbl
	    mov     eax,		0  ; exit with return code 0
        ret


_MainProc ENDP
END                             ; end of source code