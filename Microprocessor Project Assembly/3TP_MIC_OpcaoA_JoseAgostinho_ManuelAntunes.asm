#INCLUDE <P16F627.INC> 
COUNT EQU 0X20

;OPCAO A
START 

;DESLIGAR COMPARADORES
	MOVLW 0X07
	MOVWF CMCON

;SELECIONAR BANCO 1 PARA ENTRADAS E SAIDAS
	BCF STATUS , 6 
	BSF STATUS , 5 

;DEFENIR ENTRADAS E SAIDAS
	;ENTRADAS
	BSF TRISA , 0 ;PARA SIMULAR A CHEGADA DE UM COMBOIO
	BSF TRISA , 4 ;PARA SIMULAR A LINHA 1
	BSF TRISA , 5 ;PARA SIMULAR A LINHA 2
	BSF TRISA , 6 ;PARA SIMULAR A LINHA 3

	;SAIDAS
	BCF TRISA , 1 ;PARA SIMULAR A COR DA LINHA 1
	BCF TRISA , 2 ;PARA SIMULAR A COR DA LINHA 2
	BCF TRISA , 3 ;PARA SIMULAR A COR DA LINHA 3
	;TUDO RB0 COMO SAIDA PARA O DISPLAY
	MOVLW 0X00
	MOVWF TRISB
			
;SELECIIONAR BANCO 0 COMO CODIGO PRINCIPAL
	BCF STATUS , 6 
	BCF STATUS , 5 

;CODIGO	
	;LIGAR O DISPLAY A 1
	MOVLW B'00000110' ;VALOR INICIAL A 1
	MOVWF PORTB
	
	;LIGAR OS LEDS DE CADA LINHA
	BSF PORTA ,1
	BSF PORTA ,2
	BSF PORTA ,3

;COMECO DO CICLO	
CICLO
	BTFSS PORTA , 0	;ENQUANTO RA0 NAO FOR ACIONADO
	GOTO CICLO
	GOTO CHEGADA

;CHEGADAS DOS COMBOIOS	
CHEGADA		
	BTFSC PORTA , 1		;CASO  HAJA COMBOIOS NA LINHA 1
	GOTO LINHA_1_CHEGADA	;VAI METER O COMBOIO NA LINHA 1			
	BTFSC PORTA , 2		;CASO HAJA COMBOIOS NA LINHA 2
	GOTO LINHA_2_CHEGADA	; VAI METER O COMBOIO NA LINHA 3	
	BTFSC PORTA , 3		;CASO HAJA COMBOIOS NA LINHA 3	
	GOTO LINHA_3_CHEGADA	;VAI METER O COMBOIO NA LINHA 3
	GOTO PARTIDAS			

LINHA_1_CHEGADA	
	BCF PORTA , 1	;LED LINHA 1 OFF
	GOTO VERIFICA_DISPLAY 	;ATUALIZA O DISPLAY
CONTINUA_1_CHEGADA			;CONTINUA O DEPOIS DE ATUALIZAR O DISPLAY
	GOTO ESPERA_CHEGADA		;PARA VERIFICAR SE O BOTAO DO RA0 FOI DESLIGADO

LINHA_2_CHEGADA
	BCF PORTA , 2	;LED LINHA 2 OFF
	GOTO VERIFICA_DISPLAY
CONTINUA_2_CHEGADA
	GOTO ESPERA_CHEGADA		;PARA VERIFICAR SE O BOTAO DO RA0 FOI DESLIGADO

LINHA_3_CHEGADA
	BCF PORTA , 3 ;LED LINHA 3 OFF
	GOTO VERIFICA_DISPLAY
CONTINUA_3_CHEGADA
	GOTO ESPERA_CHEGADA		;PARA VERIFICAR SE O BOTAO DO RA0 FOI DESLIGADO
	
;PARTIDAS DOS COMBOIOS
PARTIDAS
LINHA_1_PARTIDA
	BTFSS PORTA , 4 		;VE SE O RA4 FOI PRECIONADO
	GOTO LINHA_2_PARTIDA	;VAI VER SE O RA5 FOI PRECIONADO
	GOTO LINHA_1_MANDAR_EMBORA	;LINHA 1 COMBOIO A PARTIR

LINHA_1_MANDAR_EMBORA
	CALL DELAY		;PARA DAR TEMPO PARA O COMBOIO IR EMBORA
	MOVLW B'00000110' ;VALOR DO DISPLAY A 1
	MOVWF PORTB
	BSF PORTA , 1 ;LINHA 1 LED ON
	GOTO ESPERA_1_PARTIDA	;PARA VERIFICAR SE O BOTAO DO RA4 FOI DESLIGADO

LINHA_2_PARTIDA
	BTFSS PORTA , 5		;VE SE O RA5 FOI PRECIONADO
	GOTO LINHA_3_PARTIDA	;VAI VER SE O RA6 FOI PRECIONADO	
	GOTO LINHA_2_MANDAR_EMBORA	;LINHA 2 COMBOIO A PARTIR

LINHA_2_MANDAR_EMBORA
	CALL DELAY		;PARA DAR TEMPO PARA O COMBOIO IR EMBORA
	MOVLW B'01011011' ;VALOR DO DISPLAY A 2
	MOVWF PORTB
	BSF PORTA ,2 ; LINHA 2 LED ON
	GOTO ESPERA_2_PARTIDA	;PARA VERIFICAR SE O BOTAO DO RA5 FOI DESLIGADO

LINHA_3_PARTIDA
	BTFSS PORTA , 6		;VE SE O RA6 FOI PRECIONADO
	GOTO PARTIDAS		;SE AINDA NAO FOI MANDADO NENHUM COMBOIO EMBORA VOLTA A VERIFICAR AS PARTIDAS
	GOTO LINHA_3_MANDAR_EMBORA	;LINHA 3 COMBOIO A PARTIR

LINHA_3_MANDAR_EMBORA
	CALL DELAY		;PARA DAR TEMPO PARA O COMBOIO IR EMBORA
	MOVLW B'01001111' ;VALOR DO DISPLAY A 3
	MOVWF PORTB
	BSF PORTA , 3
	GOTO ESPERA_3_PARTIDA	;PARA VERIFICAR SE O BOTAO DO RA6 FOI DESLIGADO



ESPERA_CHEGADA	;PARA VERIFICAR SE O BOTAO DO RA0 FOI DESLIGADO
	BTFSC PORTA , 0
	GOTO ESPERA_CHEGADA
	GOTO CICLO	;VOLTA PARA O INICIO
	
ESPERA_1_PARTIDA ;PARA VERIFICAR SE O BOTAO DO RA4 FOI DESLIGADO
	BTFSC PORTA , 4
	GOTO ESPERA_1_PARTIDA
	GOTO CICLO	;VOLTA PARA O INICIO

ESPERA_2_PARTIDA ;PARA VERIFICAR SE O BOTAO DO RA5 FOI DESLIGADO
	BTFSC PORTA , 5
	GOTO ESPERA_2_PARTIDA
	GOTO CICLO	;VOLTA PARA O INICIO

ESPERA_3_PARTIDA ;PARA VERIFICAR SE O BOTAO DO RA6 FOI DESLIGADO
	BTFSC PORTA ,6 
	GOTO ESPERA_3_PARTIDA
	GOTO CICLO	;VOLTA PARA O INICIO

;PARA ATUALIZAR O DISPLAY
VERIFICA_DISPLAY
	BTFSC PORTA , 1	;SE A LINHA 1 TIVER OCUPADA
	GOTO DISPLAY_1
	BTFSC PORTA , 2	;SE A LINHA 2 TIVER OCUPADA
	GOTO DISPLAY_2
	BTFSC PORTA , 3	;SE A LINHA 3 TIVER OCUPADA
	GOTO DISPLAY_3
	GOTO DISPLAY_0	;SE TODAS AS LINHAS TIVEREM OCUPADAS
	
;DISPLAY DO VALOR 1	
DISPLAY_1
	MOVLW B'00000110' ;VALOR A 1
	MOVWF PORTB
	GOTO CONTINUA_1_CHEGADA	;VAI PARA O CODIGO DE CHEGADA DA LINHA 1

;DISPLAY DO VALOR 2
DISPLAY_2
	MOVLW B'01011011' ;VALOR A 2
	MOVWF PORTB
	GOTO CONTINUA_2_CHEGADA ;VAI PARA O CODIGO DE CHEGADA DA LINHA 2

;DISPLAY DO VALOR 3
DISPLAY_3
	MOVLW B'01001111' ;VALOR A 3
	MOVWF PORTB
	GOTO CONTINUA_3_CHEGADA ;VAI PARA O CODIGO DE CHEGADA DA LINHA 3

;DISPLAY DO VALOR 0 	
DISPLAY_0
	MOVLW B'00111111' ;VALOR A 0
	MOVWF PORTB
	GOTO PARTIDAS
	

;CODIGO DO DELAY
DELAY
	CLRF COUNT
CICLODELAY
	INCFSZ COUNT , 1
	GOTO CICLODELAY
	RETURN	

END    