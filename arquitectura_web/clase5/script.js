// Botones
const btnRegistrar = document.getElementById("btn-registrar");
const btnReiniciar = document.getElementById("btn-reiniciar");

// Inputs
const inputValor = document.getElementById("input-valor");

// Texto
const txtCantidad = document.getElementById("cantidad");
const txtPromedio = document.getElementById("promedio");
const txtMenor = document.getElementById("menor");
const txtMayor = document.getElementById("mayor");

inputValor.focus();

const cargarEstadisticas = (x) => {
  txtCantidad.textContent = x.cantidad;
  txtPromedio.textContent = parseFloat(x.promedio).toFixed(2);
  txtMenor.textContent = x.menor;
  txtMayor.textContent = x.mayor;
}

let estadisticas = {
  cantidad: 0,
  promedio: 0,
  menor: 0,
  mayor: 0,
  total: 0
}

btnRegistrar.addEventListener("click", () => {
  let valor = inputValor.value;
  try {
    valor = Number(valor);
  } catch (error) {
    alert("Error: El valor debe ser un número");
    inputValor.focus();
    return;
  }
  estadisticas.cantidad++;
  estadisticas.total += valor;
  estadisticas.promedio = estadisticas.total / estadisticas.cantidad;
  estadisticas.menor = estadisticas.menor != 0 ? Math.min(estadisticas.menor, valor) : valor;
  estadisticas.mayor = estadisticas.mayor != 0 ? Math.max(estadisticas.mayor, valor) : valor;
  cargarEstadisticas(estadisticas);
  inputValor.value = "";
  inputValor.focus();
});

btnReiniciar.addEventListener("click", () => {
  estadisticas = {
    cantidad: 0,
    promedio: 0,
    menor: 0,
    mayor: 0,
    total: 0
  }
  cargarEstadisticas(estadisticas);
  inputValor.focus();
});

