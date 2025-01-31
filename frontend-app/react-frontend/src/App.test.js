
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import '@testing-library/jest-dom';
import App from './App';
import fetchRoman from './fetchRoman';

//user event test fetch
//type in number then make sure text state updates
//click button and ensure fetch is executed by checking roman numeral text

jest.mock('./fetchRoman', () => jest.fn());

beforeEach(() => {
  fetchRoman.mockClear(); // Reset mock before each test
});

test('Basic Click Update Functionality', async () => {
  fetchRoman.mockResolvedValue("XXIV");

  let app = render(<App />);
  expect(app.getByTestId("output")).toHaveTextContent('');
  const textfield = app.getByTestId("input");
  await userEvent.type(textfield, "24");

  await userEvent.click(app.getByTestId("fetch-button"));

  await waitFor(() => {
    expect(fetchRoman).toHaveBeenCalledTimes(1);
    expect(fetchRoman).toHaveBeenCalledWith("24");
  });

  // Wait for the output text to update
  await waitFor(() => {
    expect(screen.getByText('XXIV')).toBeInTheDocument();
  });
});

test('Basic Light/Dark Mode Functionality', async () => {
  let app = render(<App />);
  await waitFor(() => {
    expect(screen.getByText('Light Mode')).toBeInTheDocument();
  });
  await userEvent.click(app.getByTestId("switch"));
  await waitFor(() => {
    expect(screen.getByText('Dark Mode')).toBeInTheDocument();
  });
});
