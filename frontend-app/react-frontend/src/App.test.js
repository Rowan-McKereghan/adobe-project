
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import '@testing-library/jest-dom';
import App from './App';

test('renders learn react button', () => {
  let app = render(<App />);
  const buttonElement = app.getByTestId('button');
  expect(buttonElement).toBeInTheDocument();
});
