# 1. Anàlisi i documentació de l'arquitectura MVVM

## 1.1. Documentació dels components (basant-vos en MVVMSimple)

Explicació breu de la responsabilitat de cada component de l'arquitectura, prenent com a referència l'estructura inicial de MVVMSimple.

- **Model: Quines dades guarda i quina és la seva funció principal?**
  Counter i message.
- **ViewModel: Quina feina fa exactament? Com es comunica amb el Model i com exposa les dades a la interfície?**
  Guarda el estado y indica cuantas veces se ha usado el boton en counter.
- **View (UI / Compose): Quin és el seu paper? Com rep els canvis d'estat i com notifica les accions de l'usuari al ViewModel?**
  Cambia la vista cuando cambia el estado. Los recibe a traves de status.value.

## 1.2. Anàlisi comparatiu (MVVMSimple vs MVVM-separatModel)

### 1. Estructura i Separació de Responsabilitats

- **Quina diferència hi ha entre la forma d'organitzar el Model a MVVMSimple i a MVVM-separatModel?**
  Simple solo contiene datos y separat a parte de tener datos tambien hace todo el "trabajo".
- **On s'executa la lògica de negoci del joc (comprovar combinacions, girar cartes, etc.) en cada un dels fitxers?**
  La logica se ejecuta en el separatModel.

### 2. Immutabilitat i Gestió de l'Estat

- **Com es modifiquen les dades del tauler a MVVM-separatModel? Quin paper juga el mètode `.copy()` i per què és important per a l'arquitectura reactiva?**
  El tablero no se modifica nunca directamente. Con .copy() se crea una copia nueva del tablero a partir de BoardModel. Eso hace que se comparen los valores antiguos con los nuevos y asegura que no hayan errores sino que solo hayan valores limitados.

### 3. Avantatges de la separació

- **Per què creieu que és millor desconnectar la lògica de negoci (BoardModel) del propi ViewModel a mesura que l'aplicació creix?**
  Tenerlo fuera del ViewModel reduce el tamaño de las clases i permite hacer tests o reutilizar y cambiar valores de la UI.

## 1.3. Anàlisi comparatiu

a) `StateFlow` / `MutableStateFlow`
b) `mutableStateOf` / `State<T>`

- **Descriu per a què es fan servir els següents mecanismes**
    - a) Muestra el estado desde viewModel.
    - b) Al cambiar su valor, cambia la estructura de los composables.
- **Quines són les diferències entre ells**
    - a) Se puede usar fuera de compose, pueden usarse comparadores, no pertenece a la UI.
    - b) Ligado solo a compose, sin comparadores, pertenece a la UI.
- **Quin escolliries per l'aplicació i per què?**
  En este caso es mejor separar el viewModel del compose porque vacia la clase y es mejor para casos simples.