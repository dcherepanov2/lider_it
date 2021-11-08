# lider_it

# Обязательно:

Заменить указанные данные ниже на cвои учетки к админке:

#spring.datasource.url=jdbc:postgresql://localhost:5433/liderIT
#spring.datasource.username=postgres
#spring.datasource.password=123

Выполнить скрипт в pgadmin(знаю, что должен быть в liquebase, но при попытке его добавить валится стектрейс, не успел разобраться с этим):


CREATE OR REPLACE FUNCTION public.filterbyparambooks(
	var1 character varying,
	var2 integer,
	var3 boolean)
    RETURNS SETOF books 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
  BEGIN
  
  	--name в book не может быть null, поэтому передача туда null означает что фильтр по имени не передавался
	--номер книги не может быть -1, поэтому передача в var2 -1 означает что параметр фильтрации "номер" указан не был
	--если var4 = -1, то и var 3 не передавался
	
  	IF var1 != 'null' AND var2 = -1 AND var3 IS NULL THEN
		RETURN QUERY SELECT * FROM books book WHERE book.name = var1;
	END IF;
	
    IF var2 != -1 AND var1 = 'null' AND var3 IS NULL THEN
    	RETURN QUERY SELECT * FROM books book WHERE book.number = var2;
	END IF;
	
	IF var3 IS NOT NULL AND var1 = 'null' AND var2 = -1 THEN
    	RETURN QUERY SELECT * FROM books book WHERE book.availability = var3;
	END IF;
	
	--001 010 100 +++
	
	IF var1 != 'null' AND var2 != -1 AND var3 IS NULL THEN
		RETURN QUERY SELECT * FROM books book WHERE book.name = var1 AND book.number = var2;
	END IF;
	
	--110 +
	
	IF var1 != 'null' AND var2 = -1 AND var3 IS NOT NULL THEN
		RETURN QUERY SELECT * FROM books book WHERE book.name = var1 AND book.availability = var3;
	END IF;
	
	--101
	
	IF var2 != -1 AND var1 != 'null' AND var3 IS NOT NULL THEN
		RETURN QUERY SELECT * FROM books book WHERE book.name = var1 AND book.number = var2 AND book.availability = var3;
	END IF;
	
	--111
	
	IF var1 = 'null' AND var2 != -1 AND var3 IS NOT NULL THEN
		RETURN QUERY SELECT * FROM books book WHERE book.number = var2 AND book.availability = var3;
	END IF;
	--011
	
    RETURN;
	END;
  
$BODY$;
