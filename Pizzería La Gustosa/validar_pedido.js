//Javascript se iniciará con el DOM cargado
$(document).ready(function(){

    //llamada asíncrona a fichero JSON con AJAX - integrada en la función "madre"
    $.ajax({
        'type'  : 'GET',
        'url'   : "http://127.0.0.1:5500/ingredientes_tamaños.json",
        'async' : true, 
    })
    .done(cargarDatos)
    .fail(fail)
    
    //función de llamada asíncrona para refrescar
    function refrescar() {
        $.ajax({
            'type'  : 'GET',
            'url'   : "http://127.0.0.1:5500/ingredientes_tamaños.json",
            'async' : true, 
        })
        .done(cargarDatos)
        .fail(fail)
    }
    //Se realiza la llamada al apretar al botón refrescar
    $("#refresh").click(refrescar)

    //Si falla la llamada
    function fail(){
        alert("Activa el Live Server!!")
    }

    //Ambas llamadas usan la siguiente función para cargar los contenidos
    function cargarDatos(ingredientes_tamaños){

    //Tamaños de pizza:
        //Variable para simplificar el código posterior
        let tamañosJson = ingredientes_tamaños.TAMAÑO
        //Se declara un array que añadirá en el DOM los tamaños
        let array_tamaños = []
        //función .each de jquery
        $.each(tamañosJson, function(){
            array_tamaños.push(`<input type='radio' name='tamaño' id=${this.ID}
            value=${this.ID} /><label for=${this.ID}> ${this.NAME} </label>`)
        })
        //con "html" no es necesario borrar el contenido ya que su uso destruye el contenido anterior
        $("#tamaño").html("<legend>Escoge el tamaño de tu pizza: </legend>"+array_tamaños.join(" "))

    //Ingredientes:
        //Variable para simplificar el código posterior
        let ingredientesJson = ingredientes_tamaños.INGREDIENTES
        //Se declara un array que añadirá en el DOM los ingredientes
        let array_ingredientes = []
        //Condición para rellenar los ingredientes en columnas de 6:
        if (ingredientesJson.length <= 6){
            $.each(ingredientesJson, function(){
                array_ingredientes.push(`<div class='fila'><label for= ${this.ID}> ${this.NAME} </label>"+
                "<input type='checkbox' name='ingrediente' id=${this.ID} value=${this.NAME} /></div>`)
            })
        }
        //Si hay más de 6 ingredientes, los reparte en columnas de 6 (bucle for clásico para ello):
        else {
            array_ingredientes.push("<div class='columna1'>")
            for (i=0; i < 6; i++){
                array_ingredientes.push("<div class='fila'><label for="+ingredientesJson[i].ID+"> "
                + ingredientesJson[i].NAME+" </label>"+"<input type='checkbox' name='ingrediente' id=" 
                + ingredientesJson[i].ID +" value=" +ingredientesJson[i].NAME+" /></div>")
            }
            array_ingredientes.push("</div> <div class='columna2'>")
            for (i=6; i < 12; i++){
                array_ingredientes.push("<div class='fila'><label for="+ingredientesJson[i].ID+"> "
                + ingredientesJson[i].NAME+" </label>"+"<input type='checkbox' name='ingrediente' id=" 
                + ingredientesJson[i].ID +" value=" +ingredientesJson[i].NAME+" /></div>")
            }
            array_ingredientes.push("</div> <div class='columna3'>")
            for (i=12; i < ingredientesJson.length; i++){
                array_ingredientes.push("<div class='fila'><label for="+ingredientesJson[i].ID+"> "
                + ingredientesJson[i].NAME+" </label>"+"<input type='checkbox' name='ingrediente' id=" 
                + ingredientesJson[i].ID +" value=" +ingredientesJson[i].NAME+" /></div>")
            }
            array_ingredientes.push("</div>")
        }
        $("#ingredientes").html(array_ingredientes.join(" "))
    }
    
    //función que recoge todos los mecanismos de validación solicitados (proviene de la actividad 3)
    function validar(){
        
        //Expresiones regulares
        let mayus = /[A-Z]./
        let tlf = /\d{3}\s*\d{3}\s*\d{3}\s*/ //que coincida con xxx xxx xxx o xxxxxxxxx
        let mail = /^[^@]+@[^@]+\.[a-zA-Z0-9]{2,}$/   //No puede empezar con "@", ha de contener un "@" y un ".", el dominio debe ser de al menos 2 caracteres
        
        //Aseguro que estén todos los datos de texto rellenados
        if ($("#nombre").val().trim() == ""  
         || $("#direccion").val().trim() == ""
         || $("#telefono").val().trim() == "" 
         || $("#email").val().trim() == "") { 
            alert("Debes completar todos tus datos personales")
            return false
        }
        //Se advierte de que el nombre debe empezar por mayúscula
        if (!mayus.test($("#nombre").val().trim())){
            alert("Tu nombre debe empezar por mayúscula")
            return false
        }
        //Se advierte de que el teléfono debe ser correcto
        if (!tlf.test($("#telefono").val().trim())){
            alert("debes facilitar un teléfono de contacto correcto")
            return false
        }
        //Se advierte de que el email debe de ser correcto
        if (!mail.test($("#email").val().trim())){
            alert("debes facilitar un correo electrónico válido")
            return false
        }
        //Aseguro que se haya seleccionado un tamaño de pizza - simplificado con jquery
        let check_tamaño = $("input[type=radio]:checked")
        if (check_tamaño.length == 0){
            alert("Debes de indicar el tamaño de tu pizza")
            return false
        }
        //Confirmar si hay 1 ingrediente seleccionado - simplificado con jquery
        let check_ingredientes = $("input[type=checkbox]:checked")
        if (check_ingredientes.length == 0){
            alert("Debes elegir al menos un ingrediente")
            return false
        }
        //Con todo comprobado devuelve true
        return true
    }

    //Función para verificar el pedido y confirmarlo
    function confirmar(){
        //Si la función de validar retorna true
        if (validar()){
            
            //Se muestra la ventana modal
            $("#ventana").css("display","block")
            $("body").css("position", "static")
            $("body").css("height", "100%")
            $("body").css("overflow", "hidden")

            //llamada asíncrona a fichero JSON con AJAX
            $.ajax({
                'type'  : 'GET',
                'url'   : "http://127.0.0.1:5500/ingredientes_tamaños.json",
                'async' : true, 
            })
            .done(confirmaDatos)
            .fail(fail)

            function confirmaDatos(ingredientes_tamaños){
            //Extrayendo el tamaño y el precio seleccionado
                //variable con tamaños de JSON
                let tamañosJson = ingredientes_tamaños.TAMAÑO
                //variable con tamaños cargados en el DOM, de este modo es posible saber cuál fue seleccionado
                var tamaño_seleccionado = $("input[name=tamaño]")
                var pvp_tamaño = 0
                //Se muestra el tamaño y el precio extraido del archivo JSON, y se almacena el precio
                $.each(tamañosJson, function(i){
                    if (tamaño_seleccionado[i].checked){
                        $("#p1").html(`Pizza ${this.ID}: ${this.PRECIO} €`)
                        pvp_tamaño = this.PRECIO
                        //"break" para $.each()
                        return false
                    }
                })
            //Extrayendo los ingredientes y los precios seleccionados
                //variable con ingredientes de JSON
                let ingredientesJson = ingredientes_tamaños.INGREDIENTES
                //variable con ingredientes cargados en el DOM, de este modo es posible saber cuáles fueron seleccionados
                var ingredientes_seleccionados = $("input[name=ingrediente]")
                //Array para almacenar ingredientes seleccionados
                var respuesta_ingredientes = []
                var sumatorio_pvp = 0
                //Se almacenan ingredientes y precios, extraidos del archivo JSON
                $.each(ingredientesJson, function(i){
                    if (ingredientes_seleccionados[i].checked){
                        respuesta_ingredientes.push(`${this.NAME}: ${this.PRECIO} € <br/>`)
                        sumatorio_pvp += this.PRECIO
                    }
                })
                //Se muestran los ingredientes y su precio
                $("#p2").html("Ingredientes <br/>" + respuesta_ingredientes.join(" "))
                
                //Se muestra el precio total
                var precio_final = pvp_tamaño + sumatorio_pvp
                $("#p3").html("Total: " + precio_final + " €")
            }

            //Función para cerrar ventana modal y resetear el pedido
            function cierra_modal() {
                $("#ventana").css("display", "none")
                $("body").css("position", "inherit")
                $("body").css("height", "auto")
                $("body").css("overflow", "visible")
                $("#p1").html("")
                $("#p2").html("")
                $("#p3").html("")
            }
            //En lugar de únicamente cerrar la ventana, se enviaría al servidor
            $("#confirmacion").click(cierra_modal)
            $(".cerrar").click(cierra_modal)
        }
    }
    //Al clicar el botón, se dispara el script
    $("#boton").click(confirmar)
})
