from flask import Flask, request, jsonify, session, redirect, url_for
from pymongo import MongoClient
from bson import ObjectId
from flask_cors import CORS
from bson import json_util
from werkzeug.security import generate_password_hash, check_password_hash

app = Flask(__name__)
app.secret_key = 'mint'

# เชื่อมต่อ MongoDB
client = MongoClient('localhost', 27017)
db = client['Promotion']
collection = db['list']

# ตรวจสอบว่ามีการลงชื่อเข้าใช้แล้วหรือไม่
def is_logged_in():
    return 'user_id' in session

# เพิ่มข้อมูลผู้ใช้ตัวอย่าง (mock user)
users = [
    {"id": 1, "username": "admin1", "password": generate_password_hash("password1")},
    {"id": 2, "username": "admin2", "password": generate_password_hash("password2")},
]

# เข้าสู่ระบบ
@app.route('/login', methods=['POST'])
def login():
    if is_logged_in():
        return jsonify(message='Already logged in')

    username = request.json.get('username')
    password = request.json.get('password')

    user = next((user for user in users if user['username'] == username), None)
    if user and check_password_hash(user['password'], password):
        session['user_id'] = user['id']
        return jsonify(message='Login successful', user=user)
    else:
        return jsonify(message='Invalid credentials'), 401

# ออกจากระบบ
@app.route('/logout', methods=['GET'])
def logout():
    session.pop('user_id', None)
    return jsonify(message='Logout successful')


# ดึงข้อมูลทั้งหมดจาก MongoDB
@app.route('/api', methods=['GET'])
def get_data():
    data_from_db = list(collection.find({}, {
        '_id': 1,
        'name':1,
        'description':1,
        'count':1,
        'id':1,
        'exp':1,
        'discount':1
    }))
    json_data = json_util.dumps(data_from_db)
    return json_data

# เพิ่มข้อมูลใหม่
@app.route('/api', methods=['POST'])
def create_data():
    data = request.get_json()
    # หา `id` ที่มีค่าสูงสุด
    max_id = collection.find_one(sort=[('id', -1)])['id']
    data['id'] = max_id + 1
    # เพิ่ม ObjectId ในฝั่ง Backend เพื่อให้ MongoDB จัดการ ID
    data['_id'] = ObjectId()
    collection.insert_one(data)
    return jsonify({'message': 'Data created successfully'}), 201

# ลบข้อมูล
@app.route('/api/<string:data_id>', methods=['DELETE'])
def delete_data(data_id):
    # ก่อนที่จะลบข้อมูล ให้เก็บค่า `id` ของข้อมูลที่จะลบ
    deleted_data = collection.find_one({'_id': ObjectId(data_id)})
    collection.delete_one({'_id': ObjectId(data_id)})
    
    # หากข้อมูลถูกลบสำเร็จ ให้ทำการเรียงลำดับข้อมูลใหม่
    if deleted_data:
        reindex_data()
    
    return jsonify({'message': 'Data deleted successfully'})

# เมธอดสำหรับเรียงลำดับข้อมูลใหม่
def reindex_data():
    all_data = list(collection.find())
    for index, data in enumerate(all_data, start=1):
        collection.update_one({'_id': data['_id']}, {'$set': {'id': index}})

# อัปเดตข้อมูล
@app.route('/api/<string:data_id>', methods=['PUT'])
def update_data(data_id):
    data = request.get_json()
    collection.update_one({'_id': ObjectId(data_id)}, {'$set': data})
    return jsonify({'message': 'Data updated successfully'})


# อนุญาต CORS สำหรับทุก route ในแอปพลิเคชัน
CORS(app, supports_credentials=True) 

if __name__ == '__main__':
    app.run(debug=True)
