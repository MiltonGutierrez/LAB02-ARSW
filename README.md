Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW
#### AUTORES:
- [Saray Mendivelso](https://github.com/saraygonm)
- [Milton Gutierrez](https://github.com/MiltonGutierrez)

####Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

#####Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

   Para poder hacer la comparación de uso de los núcleos del procesador, tomamos captura del uso de los mismos antes de la ejecución del programa:
    <p align="center">
	   <img src="img/screenshots/NucleosAntesDeLaEjecucion.png" alt="NucleosAntes" width="700px">
	</p>

   Luego despues de la ejecución verificamos nuevamente la cantidad de uso de núcleos y vimos que se usan exhaustivamente 4 nucleos para poder procesar la ejecución del programa:
   <p align="center">
	   <img src="img/screenshots/NucleosDespuesDeLaEjecucion.png" alt="NucleosDespues" width="700px">
	</p>

3. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

   Para este punto simplemente se hizo la creación de otros 3 threads adicionales, a los cuales se les dividio la cantidad de trabajo como se muestra en la siguiente imagen.
       <p align="center">
	   <img src="img/screenshots/Punto1Main2.png" alt="Main2" width="700px">
	</p>

   Para poder hacer la comparación correctamente, se observara el uso de los núcleos antes y después de la ejecución del nuevo código:

   **ANTES**
      <p align="center">
	   <img src="img/screenshots/NucleosAntesDeLaEjecucion2.png" alt="NucleosDespues" width="700px">
	</p>

   **DESPUÉS**
      <p align="center">
	   <img src="img/screenshots/NucleosDespuesDeLaEjecucion2.png" alt="NucleosDespues" width="700px">
	</p>

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo. 

   Para esto inicialmente guardamos los 3 PrimeFinderThreads en un arreglo, de tal manera que podamos acceder a ellos de manera más sencilla con el uso de For, iniciamos cada uno de los treads, y posteriormente a esto para poder controlar la ejecución de estos, utilizamos un ciclo que se ejecutara hasta que running sea false (running será true siempre que haya un thread vivo). Esperamos los 5 segundos de ejecución para con el ciclo For parar cada uno de los Threads con el método implementado *stopRunning*. Una vez parados, se contara los primos que se han encontrado hasta el momento.
      <p align="center">
	   <img src="img/screenshots/Punto3Main1.png" alt="Main" width="700px">
	</p>

Para la continuación de la ejecución se pide que el usuario presione enter, se le asigna el valor de false a la variable running, si existe algún thread que siga vivo (no haya terminado su ejecución) se le dará el valor de true para que continue, esto con el método startRunning(). Finalmente cuando se termine la ejecución del ciclo en Main, se mostrará la cantidad final y total de primos encontrados.
      <p align="center">
	   <img src="img/screenshots/Punto3Main3.png" alt="Main" width="700px">
	</p>

Ahora explicaremos como se implementaron los métodos, *run()*, *startRunnin()* y *stopRunning()* 

- **run()**
        <p align="center">
	   <img src="img/screenshots/Punto3PThreads2.png" alt="Run" width="700px">
	</p>
El ciclo se ejecutará hasta que se calcule todo el rango de números, si la variable booleana *running* es true (está es unica para cada thread), se hara el calculo de si es primo o no, de lo contrario, en un bloque sincronizado (utilizando el thread mismo como objeto) haremos que pare, esto hasta que el thread llame a notify como se mostrara en los siguiente metodos. 

- **startRunning()** y **stopRunning()**
        <p align="center">
	   <img src="img/screenshots/Punto3PThreads1.png" alt="Run" width="700px">
	</p>

Como observamos el método *startRunning()* nos cambia la variable *running* a true, y además hace que el thread se llame a sí mismo para que continue con la ejecución, esto hara que continue buscando números primos, por el otro lado *stopRunning()* simplemente cambiará el valor de la variable *running* a false, de manera que cuando vuelva a ejecutar el ciclo en *run()*, el thread se pare a si mismo. 

 
#####Parte II 


Para este ejercicio se va a trabajar con un simulador de carreras de galgos (carpeta parte2), cuya representación gráfica corresponde a la siguiente figura:

![](./img/media/image1.png)

En la simulación, todos los galgos tienen la misma velocidad (a nivel de programación), por lo que el galgo ganador será aquel que (por cuestiones del azar) haya sido más beneficiado por el *scheduling* del
procesador (es decir, al que más ciclos de CPU se le haya otorgado durante la carrera). El modelo de la aplicación es el siguiente:

![](./img/media/image2.png)

Como se observa, los galgos son objetos ‘hilo’ (Thread), y el avance de los mismos es visualizado en la clase Canodromo, que es básicamente un formulario Swing. Todos los galgos (por defecto son 17 galgos corriendo en una pista de 100 metros) comparten el acceso a un objeto de tipo
RegistroLLegada. Cuando un galgo llega a la meta, accede al contador ubicado en dicho objeto (cuyo valor inicial es 1), y toma dicho valor como su posición de llegada, y luego lo incrementa en 1. El galgo que
logre tomar el ‘1’ será el ganador.

Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal. Sin embargo, es posible que una vez corregido esto, haya más inconsistencias causadas por la presencia de condiciones de carrera.

Taller.

1.  Corrija la aplicación para que el aviso de resultados se muestre
    sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.
    Para esto tenga en cuenta:

    a.  La acción de iniciar la carrera y mostrar los resultados se realiza a partir de la línea 38 de MainCanodromo.

    b.  Puede utilizarse el método join() de la clase Thread para sincronizar el hilo que inicia la carrera, con la finalización de los hilos de los galgos.

2.  Una vez corregido el problema inicial, corra la aplicación varias
    veces, e identifique las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola (algunas veces
    podrían salir resultados válidos, pero en otros se pueden presentar
    dichas inconsistencias). A partir de esto, identifique las regiones
    críticas () del programa.

3.  Utilice un mecanismo de sincronización para garantizar que a dichas
    regiones críticas sólo acceda un hilo a la vez. Verifique los
    resultados.

4.  Implemente las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos
    deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos
    deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el lenguaje (wait y notifyAll).


## Criterios de evaluación

1. Funcionalidad.

    1.1. La ejecución de los galgos puede ser detenida y resumida consistentemente.
    
    1.2. No hay inconsistencias en el orden de llegada registrado.
    
2. Diseño.   

    2.1. Se hace una sincronización de sólo la región crítica (sincronizar, por ejemplo, todo un método, bloquearía más de lo necesario).
    
    2.2. Los galgos, cuando están suspendidos, son reactivados son sólo un llamado (usando un monitor común).

