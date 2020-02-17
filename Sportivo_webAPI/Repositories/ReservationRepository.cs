using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class ReservationRepository
    {
        public ICollection<Reservation> GetAllForUser(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservations = context.Reservations.Where(r => r.UserId == id)
                        .Include(r => r.Court.Company)
                        .ToList();
                    return reservations;
                }
            }
            catch { return null; }
        }
        public ICollection<Reservation> GetAllForCompanyOnDate(int companyId, DateTime date)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservations = context.Reservations.Where(r => r.Court.CompanyId == companyId && r.StartTime.Date == date.Date)
                        .Include(r => r.Court).ToList();
                    return reservations;
                }
            }
            catch { return null; }
        }

        public ICollection<Reservation> GetAllForDateTime(DateTime date)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservations = context.Reservations.Where( r => r.StartTime.CompareTo(date) == 0)
                        .Include(r => r.Court.Company).ToList();
                    return reservations;
                }
            }
            catch { return null; }
        }

        public Reservation Get(int id)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservation = context.Reservations.FirstOrDefault(c => c.ReservationId == id);
                    return reservation;
                }
            }
            catch { return null; }
        }

        public bool Add(Reservation reservation)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    context.Reservations.Add(reservation);
                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }
        public bool Update(Reservation reservation, Reservation updated)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    reservation.StartTime = updated.StartTime;
                    reservation.EndTime = updated.EndTime;

                    context.SaveChanges();
                    return true;
                }
            }
            catch { return false; }
        }

        public bool Delete(int reservationId, int userId)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var reservation = context.Reservations.FirstOrDefault(r => r.ReservationId == reservationId);
                    var user = context.Users.FirstOrDefault(u => u.UserId == userId);
                    if (reservation.UserId == userId || reservation.Court.CompanyId == user.CompanyId)
                    {
                        context.Reservations.Remove(reservation);
                        context.SaveChanges();
                        return true;
                    }
                    return false;
                }
            }
            catch { return false; }
        }
    }
}
