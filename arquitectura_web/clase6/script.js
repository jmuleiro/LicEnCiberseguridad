// Traer elemento div contenedor
const contenedor = document.getElementById("contenedor");

// Traer info de personajes de la API de Star Wars
const xhr = new XMLHttpRequest();
xhr.open("GET", "https://swapi.info/api/people/");

xhr.onload = function () {
  // Verifica el HTTP status code
  if (xhr.status >= 200 && xhr.status < 300) {
    // Convierte la respuesta a JSON
    const data = JSON.parse(xhr.responseText);

    // Inicializar variables locales
    let html = "";
    let contador = 0;

    // Iterar por cada personaje
    data.forEach(personaje => {
      // Limitar a 10 personajes
      if (contador < 10) {
        contador++;
        // Agregar info del personaje al HTML
        html += `
          <div>
            <h2>${personaje.name}</h2>
            <p><strong>Altura:</strong> ${personaje.height}</p>
            <p><strong>Peso:</strong> ${personaje.mass}</p>
            <p><strong>Color de pelo:</strong> ${personaje.hair_color}</p>
            <p><strong>Género:</strong> ${personaje.gender}</p>
          </div>
        `;
      } else {
        return;
      }
    });

    // Cargar el HTML
    contenedor.innerHTML = html;
  } else {
    // Manejo de errores HTTP
    console.error("Error HTTP: ", xhr.statusText);
    contenedor.innerHTML = "<p>Error de carga.</p>";
  }
};

// Manejo de errores
xhr.onerror = function (error) {
  console.error("Error:", error);
  contenedor.innerHTML = "<p>Error de carga.</p>";
};

// Enviar la request
xhr.send();
