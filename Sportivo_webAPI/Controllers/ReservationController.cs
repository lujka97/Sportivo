using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Primitives;
using Sportivo_webAPI.Models;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReservationController : Controller
    {

        private readonly ReservationRepository _reservationRepository;
        public ReservationController()
        {
            _reservationRepository = new ReservationRepository();
        }
        [HttpGet]
        [Route("get")]
        public IActionResult Get(int companyId, DateTime date)
        {
            return Ok(_reservationRepository.GetAllForCompanyOnDate(companyId, date));
        }

        [HttpGet]
        [Route("getOnDateTime")]
        public IActionResult GetOnDateTime(DateTime date)
        {
            return Ok(_reservationRepository.GetAllForDateTime(date));
        }

        [HttpGet]
        [Route("getMy")]
        [Authorize]
        public IActionResult GetAllForUser()
        {
            StringValues headerValues;

            if (Request.Headers.TryGetValue("Authorization", out headerValues))
            {
                string token_str = headerValues.First();
                string[] token_arr = token_str.Split(" ");
                var handler = new JwtSecurityTokenHandler();
                var token = handler.ReadJwtToken(token_arr[1]);

                var user = token.Payload["unique_name"];

                int userId = int.Parse(user.ToString());

                return Ok(_reservationRepository.GetAllForUser(userId));
            }

            return BadRequest();
        }

        [HttpPost]
        [Route("create")]
        [Authorize]
        public IActionResult Create([FromBody]Reservation reservation, DateTime start, DateTime end)
        {
            StringValues headerValues;
            //Reservation reservation = new Reservation(startTime, endTime, courtId);
            var a = Request.Body;

            reservation.StartTime = start;
            reservation.EndTime = end;

            if (Request.Headers.TryGetValue("Authorization", out headerValues))
            {
                string token_str = headerValues.First();
                string[] token_arr = token_str.Split(" ");
                var handler = new JwtSecurityTokenHandler();
                var token = handler.ReadJwtToken(token_arr[1]);

                var userId = token.Payload["unique_name"];

                reservation.UserId = int.Parse(userId.ToString());

                return Ok(_reservationRepository.Add(reservation));
            }

            return BadRequest();
        }

        [HttpDelete]
        [Route("delete")]
        [Authorize]
        public IActionResult Delete(int reservationId)
        {
            StringValues headerValues;
            var a = Request.Body;

            if (Request.Headers.TryGetValue("Authorization", out headerValues))
            {
                string token_str = headerValues.First();
                string[] token_arr = token_str.Split(" ");
                var handler = new JwtSecurityTokenHandler();
                var token = handler.ReadJwtToken(token_arr[1]);

                var userId = token.Payload["unique_name"];

                return Ok(_reservationRepository.Delete(reservationId, int.Parse(userId.ToString())));
            }

            return BadRequest();
        }

    }
}
