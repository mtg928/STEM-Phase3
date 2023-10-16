/** @type {import('tailwindcss').Config} */
import withMT from "@material-tailwind/react/utils/withMT"

module.exports = withMT({
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./index.html"
  ],
  theme: {
    extend: {},
    fontFamily: {
      sans: ['Inter', 'system-ui', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', 'Arial', 'Noto Sans', 'sans-serif'],
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
})
