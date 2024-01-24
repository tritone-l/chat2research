const axios = require('axios');

const fetchData = async () => {
  try {
    const response = await axios.get('http://127.0.0.1/docs');
    return response.data;
  } catch (error) {
    console.error('API 请求错误:', error.message);
    return [];
  }
};

module.exports = { fetchData };