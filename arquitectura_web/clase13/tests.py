import unittest
import requests


class RestClient():
  endpoint = "http://localhost:8080"

  def _get(self, path: str, args: str = "", timeout: int = 30) -> requests.Response:
    if not path.startswith('/'):
      path = f'/{path}'
    
    if args:
      args = f"?{args}"

    r = requests.get(f"{self.endpoint}{path}{args}", timeout=timeout)
    r.raise_for_status()
    return r

  def get(self, path: str, args: str = "", timeout: int = 30):
    try:
      return self._get(path, args, timeout).status_code
    except Exception as e:
      print(f"Error: {e}")
      raise
  
  def get_with_body(self, path: str, args: str = "", timeout: int = 30):
    try:
      r = self._get(path, args, timeout)
      return (r.status_code, r.content.decode('utf-8'))
    except Exception as e:
      print(f"Error: {e}")
      raise


class TestSuite(unittest.TestCase):
  def test_root(self):
    self.assertEqual(200, RestClient().get('/'))
  
  def test_frases(self):
    status_code, content = RestClient().get_with_body('/frases')
    self.assertEqual(200, status_code)
    self.assertLess(1, len(content))
  
  def test_mayor(self):
    status_code, content = RestClient().get_with_body('/mayor', "param1=5&param2=7")
    self.assertEqual(200, status_code)
    self.assertEqual("Número mayor: 7", content)

if __name__ == '__main__':
  unittest.main()