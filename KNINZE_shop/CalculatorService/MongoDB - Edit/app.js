const express = require('express');
const cors = require('cors');
const { connectDB, closeDB } = require('./db');

const app = express();
const port = 3000;

// ใส่ middleware cors ที่นี่
app.use(cors());


app.get('/get', async (req, res) => {

  let database;
  try {
    database = await connectDB();
    const collection = database.collection('list');
    const data = await collection.find({ }).toArray();
    const count = data.length;

    // สร้าง JSON response ที่มี properties "data" และ "count"
    const responseData = {
      จำวนรายการขาย: count,
      สินค้าที่ขาย: data,
    };

    // ส่ง JSON response ไปยังไคลเอนต์
    res.json(responseData);
  } catch (error) {
    console.error('Error handling request:', error);
    res.status(500).send('Internal Server Error');
  } finally {
    if (database) {
      await closeDB();
    }
  }
});

app.get('/date/:finddate', async (req, res) => {
  const finddate = req.params.finddate;

  let database;
  try {
    database = await connectDB();
    const collection = database.collection('list');

    // ให้ใน aggregation pipeline คำนวณค่ารวมของคอลัมน์ cost
    const aggregationPipeline = [
      { $match: { date: finddate } },
      { $group: { _id: null, sumCost: { $sum: "$cost" } } }
    ];

    const sumResult = await collection.aggregate(aggregationPipeline).toArray();
    const sumCost = sumResult.length > 0 ? sumResult[0].sumCost : null;

    const data = await collection.find({ date: finddate }).toArray();
    const count = data.length;

    // สร้าง JSON response ที่มี properties "data" และ "count"
    const responseData = {
      จำนวนรายการขาย: count,
      ยอดขายของทั้งวัน: sumCost,
      สินค้าที่ขาย: data,
      
    };

    // ส่ง JSON response ไปยังไคลเอนต์
    res.json(responseData);
  } catch (error) {
    console.error('Error handling request:', error);
    res.status(500).send('Internal Server Error');
  } finally {
    if (database) {
      await closeDB();
    }
  }
});

app.get('/month/:findmonth', async (req, res) => {
  const findmonth = req.params.findmonth;

  let database;
  try {
    database = await connectDB();
    const collection = database.collection('list');

    // ให้ใน aggregation pipeline คำนวณค่ารวมของคอลัมน์ cost
    const aggregationPipeline = [

      {
        $match: { date: { $regex: `^${findmonth}` } }
      },
      { $group: { _id: null, sumCost: { $sum: "$cost" } } }
    ];
    console.log(findmonth);

    const sumResult = await collection.aggregate(aggregationPipeline).toArray();
    const sumCost = sumResult.length > 0 ? sumResult[0].sumCost : null ;

    const data = await collection.find({
      date: { $regex: `^${findmonth}` }
    }).toArray();

    const count = data.length;

    // สร้าง JSON response ที่มี properties "data" และ "count"
    const responseData = {
      จำนวนรายการขาย: count,
      ยอดขายของทั้งเดือน: sumCost,
      สินค้าที่ขาย: data,
    };

    // ส่ง JSON response ไปยังไคลเอนต์
    res.json(responseData);
  } catch (error) {
    console.error('Error handling request:', error);
    res.status(500).send('Internal Server Error');
  } finally {
    if (database) {
      await closeDB();
    }
  }
});

app.get('/year/:findyear', async (req, res) => {
  const findyear = req.params.findyear;

  let database;
  try {
    database = await connectDB();
    const collection = database.collection('list');

    // ให้ใน aggregation pipeline คำนวณค่ารวมของคอลัมน์ cost
    const aggregationPipeline = [
      {
        $addFields: {
          monthYear: {
            $dateFromParts: {
              year: { $toInt: { $arrayElemAt: [ { $split: ["$date", "-"] }, 2 ] } },
              month: { $toInt: { $arrayElemAt: [ { $split: ["$date", "-"] }, 0 ] } },
              day: 1,
            }
          }
        }
      },
      {
        $match: { date: { $regex: `^${findyear}` } }
      },
      { $group: { _id: null, sumCost: { $sum: "$cost" } } }
    ];
    console.log(findyear);

    const sumResult = await collection.aggregate(aggregationPipeline).toArray();
    const sumCost = sumResult.length > 0 ? sumResult[0].sumCost : null ;

    const data = await collection.find({
      date: { $regex: `^${findyear}` }
    }).toArray();

    const count = data.length;

    // สร้าง JSON response ที่มี properties "data" และ "count"
    const responseData = {
      จำนวนรายการขาย: count,
      ยอดขายของทั้งปี: sumCost,
      สินค้าที่ขาย: data,
    };

    // ส่ง JSON response ไปยังไคลเอนต์
    res.json(responseData);
  } catch (error) {
    console.error('Error handling request:', error);
    res.status(500).send('Internal Server Error');
  } finally {
    if (database) {
      await closeDB();
    }
  }
});


app.listen(port, () => {
  console.log(`รันติดแล้ว ${port}`);
});