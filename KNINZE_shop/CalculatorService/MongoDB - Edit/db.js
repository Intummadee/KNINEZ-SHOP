const { MongoClient } = require('mongodb');

const uri = 'mongodb://127.0.0.1:27017/mydb';
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });

async function connectDB() {
  try {
    await client.connect();
    console.log('เชื่อมต่อ mongodb');
    return client.db('kninezshop');
  } catch (error) {
    console.error('Error connecting to the database:', error);
    throw error;
  }
}

async function closeDB() {
  try {
    await client.close();
    console.log('ปิดการเชื่อมต่อ mongodb');
  } catch (error) {
    console.error('Error closing the database connection:', error);
    throw error;
  }
}

module.exports = { connectDB, closeDB };