# Dependencias
from flask import Flask, request
import os
import json
import random

# Inicializar la aplicación
app = Flask(__name__)
port = 8080

# Endpoint root
@app.route('/')
def index():
  return 'Hola mundo!'

# Endpoint de frases, selecciona una frase al azar del archivo frases.json
@app.route('/frases', methods=['GET'])
def frases():
  with open(os.path.join(os.path.curdir, 'frases.json'), 'r') as f:
    frases = json.load(f)
  return random.choice(frases['frases'])

# Endpoint que recibe dos parámetros, los compara y si son un número devuelve
# el que sea mayor. Los parámetros son: param1 y param2 (requets args).
@app.route('/mayor', methods=['GET'])
def mayor():
  try:
    num1 = request.args.get('param1', type=int)
    num2 = request.args.get('param2', type=int)
    return f"Número mayor: {max(num1, num2)}"
  except Exception as e:
    return f'Error: {e}'

# Ejecutar el servidor
if __name__ == '__main__':
  app.run(port=port)
