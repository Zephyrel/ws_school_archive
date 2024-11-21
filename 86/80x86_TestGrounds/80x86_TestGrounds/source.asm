; Example assembly language program -- adds 158 to number in memory
; Author:  R. Detmer
; Date:    6/2013

.586
.MODEL FLAT

.STACK  4096            ; reserve 4096-byte stack

;.DATA                   ; reserve storage for data
;number  DWORD   -105
;sum     DWORD   ?

.CODE                           ; start of main program code
main    PROC

		mov ax, 0FFFEh  
		inc ax
		inc ax
        mov   eax, 0            ; exit with return code 0
        ret
main    ENDP

END     main                       ; end of source code
