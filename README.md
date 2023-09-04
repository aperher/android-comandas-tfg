# Aplicación gestión de comandas
- [Descripción](#descripción)
- [Objetivos](#objetivos)
- [Arquitectura](#arquitectura)
- [Librerías utilizadas](#librerías-utilizadas)
- [Demo](#demo)
- [Usabilidad](#usabilidad)

## Descripción
Una aplicación dinámica y adaptable que pretende agilizar el trabajo realizado por los camareros. Establece un flujo comunicativo con el personal y
obtiene feedback en tiempo real.
<div align="center">
  <img height="300" src="https://github.com/aperher/android-comandas-tfg/assets/100694428/fad69d60-3118-4058-a1c6-846937921e6e"/>
  <img height="300" src="https://github.com/aperher/android-comandas-tfg/assets/100694428/3e0fc69a-d5bf-4c35-b0a4-08ee9d97e06c"/>
  <img height="300" src="https://github.com/aperher/android-comandas-tfg/assets/100694428/67323e3d-ff4c-424b-9abd-b4e634eaec46)"/>
</div>

## Objetivos
- Desarrollar una aplicación Android que proporcione soporte a los camareros con la gestión de las comandas, con el objetivo de mejorar la eficiencia y precisión de la toma de pedidos.
-	Diseñar una herramienta funcional que pueda ser implementada en cualquier tipo de restaurante, adaptándose a las necesidades y características específicas de cada establecimiento.
- Mejorar la comunicación entre camareros y cocina, eliminando los problemas del uso de comandas tradicionales, como extravíos, errores sintácticos, repetición de trabajo realizado por otros camareros, etc. 
-	Fomentar la transición digital para simplificar, agilizar y optimizar el proceso al personal, repercutiendo de forma directa en la satisfacción del cliente.
-	Mejorar la usabilidad y costes de los softwares de gestión de comandas para empresarios hosteleros.

## Arquitectura
El patrón arquitectónico empleado en este proyecto es **Clean Architecture** de Robert C. Martin. Como patrón de presentación se ha utilizado el patrón **MVVM**. 
Aunque, también se ha experimentado con el MVI en algunos ViewModel que resultaban extensos.
<div align="center">
  <img width="450" src="https://github.com/aperher/android-comandas-tfg/assets/100694428/672c0feb-e963-48b6-ad82-eda1002ad01f"/>
</div>

Se hace uso del inyector de dependencias **HILT** y del principio de *inversion de dependencias* para que las capas internas no dependan de la 
implementación de las externas. El flujo de los datos a través de los componentes de la arquitectura:

<div align="center">
  <img width="450" src="https://github.com/aperher/android-comandas-tfg/assets/100694428/34509b47-29ad-4bd3-aef5-c08e16ec5b24"/>
</div>

## Librerías utilizadas
- Hilt
- Retrofit
- Moshi
- Room
- ViewPager
- Navigation
- RecyclerView (+swipe_decorator, +selection)
- SwipeToRefresh
- Shimmer

## Demo
![image](https://github.com/aperher/android-comandas-tfg/assets/100694428/bf12b827-2c53-42e5-ab7d-96da1a7fc219)
![image](https://github.com/aperher/android-comandas-tfg/assets/100694428/929c550a-034f-4b0f-98ea-5996cf264b9a)
![image](https://github.com/aperher/android-comandas-tfg/assets/100694428/2419fcc7-32af-4016-91cc-b87fa0445da3)

# Usabilidad
