# 🧾 CaciqueCI - Inventario de Control Interno

Este proyecto es una aplicación Android desarrollada para llevar el control de inventario de forma **offline**, permitiendo realizar registros y volcados masivos (bulk) de datos cuando se restablece la conexión a internet.

## 🗃️ Propósito

La aplicación permite gestionar de manera eficiente el inventario en cuatro áreas principales del sistema de distribución:

- 🛒 **Supermercado** (`10000`)  
- 🧍 **Cliente (Customer)** (`20000`)  
- 🏪 **Punto de venta (Sell Point)** (`30000`)  
- ➕ **Adicionales** (`10001`)  

Cada uno de estos módulos tiene un código único y su propio flujo de inventario.

## 🔌 Características principales

- 📦 Registro de productos escaneados por código de barras.  
- 📲 Uso completamente offline (sin conexión a internet).  
- 🔄 Volcado masivo de datos (Bulk) hacia el servidor cuando hay conexión.  
- 📍 Selección dinámica de *Tipo de Inventario* y *Inventario Activo*.  
- 🌓 Soporte para tema claro y oscuro.

## 🧰 Tecnologías utilizadas

- Kotlin  
- Retrofit2  
- SQLite local para persistencia offline  
- Arquitectura adaptable para extender el número de categorías

## 🚀 Cómo empezar

1. Clona el repositorio  
2. Abre el proyecto en Android Studio  
3. Conecta un dispositivo o emulador  
4. Ejecuta la aplicación
