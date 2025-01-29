import './App.css';
import {Button, defaultTheme, Provider} from '@adobe/react-spectrum';

function App() {
  return (
    (
      <Provider theme={defaultTheme}>
        <Button
          variant="accent"
          onPress={() => alert('Hey there!')}
        >
          Hello React Spectrum!
        </Button>
      </Provider>
    )
  );
}

export default App;
