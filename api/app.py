from flask import Flask, jsonify
import json


app = Flask(__name__)

with open('informacoes.js') as json_file:
    banco = json.load(json_file)


@app.route("/")
def hello_world():
    floresta = float(banco['2009']['floresta']) + float(banco['2008']['floresta']) + float(banco['2010']['floresta'])
    click = 1000000
    percent = 100/int((int(floresta)/click))
    quantidade = 1000000
    print('porcentagem: ', percent)
    print('quantidade: ', quantidade)
    dto = {'percentual': percent, 'quantidade': quantidade}
    return jsonify(dto), 200


if __name__ == '__main__':
    app.run(port=5001)