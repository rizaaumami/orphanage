const express = require('express');
const router = express.Router();
const orphanage = require('../models/orphanage');
const { ObjectId } = require('mongodb')

// Route GET untuk mendapatkan semua data tempat panti 
router.get('/get', async (req, res) => {
  try {
    const tempatPanti = await orphanage.find();
    res.json({ success: 1, message: 'Data panti asuhan berhasil ditampilkan', panti: tempatPanti });
  } catch (err) {
    res.status(500).json({ success: 0, message: err.message });
  }
});

// Route GET untuk mendapatkan data tempat panti  berdasarkan ID
router.get('/:id', async (req, res) => {
  try {
    const tempatPanti = await orphanage.findById(req.params.id);
    if (!tempatPanti) {
      return res.status(404).json({ message: ' Data tempat panti tidak ditemukan' });
    }
    res.json(tempatPanti);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Route POST untuk membuat data tempat panti baru
router.post('/add', async (req, res) => {
  const tempatPanti = new orphanage({
    nama_panti: req.body.nama_panti,
    tentang_panti: req.body.tentang_panti,
    alamat_panti: req.body.alamat_panti,
    foto_panti: req.body.foto_panti,
    telephone_panti: req.body.telephone_panti,
    panti_id:req.body.panti_id
  });

  try {
    const newtempatPanti = await tempatPanti.save();
    res.status(201).json({message: 'Data Panti Asuhan berhasil ditambahkan', data: newtempatPanti });
  } catch (err) {
    res.status(400).json({ message: 'Gagal menambahkan data panti asuhan', error: err.message });
  }
});

// Route PUT untuk memperbarui data tempat panti berdasarkan ID
router.put('/update/:id', async (req, res) => {
  try {
    const tempatPanti = await orphanage.findById(req.params.id);
    if (!tempatPanti) {
      return res.status(404).json({ message: 'Data tempat panti tidak ditemukan' });
    }

    tempatPanti.panti_id = req.body.panti_id;
    tempatPanti.nama_panti = req.body.nama_panti;
    tempatPanti.tentang_panti = req.body.tentang_panti;
    tempatPanti.alamat_panti = req.body.alamat_panti;
    tempatPanti.foto_panti = req.body.foto_panti;
    tempatPanti.telephone_panti = req.body.telephone_panti;

    const updatedtempatPanti = await tempatPanti.save();
    res.json({ message: 'Data panti asuhan berhasil diperbarui', data: updatedtempatPanti });
  } catch (err) {
    res.status(400).json({ message: 'Gagal memperbarui data panti asuhan', error: err.message });
  }
});

// Route DELETE untuk menghapus data tempat panti berdasarkan ID
router.delete('/delete/:id', async (req, res) => {
  try {
    const tempatPanti = await orphanage.findByIdAndDelete(new ObjectId(req.params.id));
    if (!tempatPanti) {
      return res.status(404).json({ message: 'Data tempat panti tidak ditemukan' });
    }

    res.json({ success: 1, message: 'Data tempat panti berhasil dihapus' });
  } catch (err) {
    res.status(500).json({ success: 0, message: 'Gagal menghapus data panti asuhan', error: err.message });
  }
});

module.exports = router;