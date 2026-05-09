// Escena
const scene = new THREE.Scene();

// Camara
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.z = 75;

// Renderer
const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

// Variables
let textMesh, fuente;

cargarFuente();

// Carga fuente para el texto
function cargarFuente() {
  const fontLoader = new THREE.FontLoader();
  fontLoader.load("https://threejs.org/examples/fonts/helvetiker_regular.typeface.json", (respuesta) => {
    fuente = respuesta;
    crearTexto();
  });
}

// Inicializa el texto
function crearTexto() {
  const textGeometry = new THREE.TextGeometry("Joaquin", {
    font: fuente,
    size: 7,
    height: 0.5,
    depth: 1.5
  });

  textGeometry.computeBoundingBox();
  const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
  textMesh = new THREE.Mesh(textGeometry, material);
  textMesh.position.set(-15, 0, 0);
  scene.add(textMesh);
  renderer.setAnimationLoop(animar);
}

// Animacion
function animar(time) {
  textMesh.position.y = Math.sin(time / 300) * 20;
  renderer.render(scene, camera);
}
